const url = 'https://zxnfjm0fbk.execute-api.us-east-2.amazonaws.com/alpha4' //modify when we change API
window.onload = () => {
	const idChoice = window.location.search.substring(window.location.search.indexOf("?idchoice=")+10, window.location.search.indexOf("?idchoice=")+46);
	console.log(idChoice);
	fetch(url+"/choice/"+idChoice, {
		method:'GET'
	})
	.then(response=>response.json())
	.then(json=>{
		console.log(json)
		if(json.statusCode==200) {
			document.querySelector('#choicedescription').innerText = json.choice.description
			const aNames = []
			const aDescrips = []
			const aIds = []
			//TODO: add support for feedback.
			let number = 0
			for(const alt in json.choice.alternatives) { //is this right syntax?
				if(json.choice.alternatives[alt]==null) break
				aNames.push(json.choice.alternatives[alt].name)
				aDescrips.push(json.choice.alternatives[alt].description)
				aIds.push(json.choice.alternatives[alt].id)
				number += 1
			}
			createCarousel(aNames, aDescrips, aIds, number)
			document.querySelector('#code').innerText = json.choice.id
		} else {
			console.log("ERROR")
		}		
	})
}

function changeRating(self, strNum, otherInt) {
        const strs = ["up", "down"]
		const memberName = localStorage.getItem('memberName')
		const rating = !self.childNodes[0].src.includes('blue')
		const idAlternative = document.querySelectorAll('#idAlternative')[otherInt].innerText
		fetch(url+"/addRating", {
			method:'POST',
			body:JSON.stringify({rating, memberName, idAlternative})
		})
		.then(response=>response.json())
		.then(json=>{
			console.log(json)
			if(json.statusCode==200) {
				//below only happens if successful
		        if(self.childNodes[0].src.includes('blue')) {
		            self.childNodes[0].src=`/img/${strs[strNum]}.png`
		        } else {
		            self.childNodes[0].src=`/img/blue${strs[strNum]}.png`
		        }
		        document.querySelectorAll(`.${strs[Math.abs(strNum-1)]}-rating`)[otherInt].src=`/img/${strs[Math.abs(strNum-1)]}.png`
		        //in here, also make sure that it deselects the other one!
			} else {
				console.log("ERROR!")
			}
		})

		
    }
function createCarousel(aNames, aDescrips, aIds, num) {
    const cindicators = document.querySelectorAll('.cli')
    const cinners = document.querySelectorAll('.carousel-item')
    for(let i=0; i<num; i++) {
            const h1 = document.createElement('h1')
            h1.innerText=aNames[i]
            const h4 = document.createElement('h4')
            h4.innerText=aDescrips[i]            
			const h6 = document.createElement('h6')
			h6.setAttribute('id', 'idAlternative')
			h6.innerText=aIds[i]
            const approval = document.createElement('img')
            approval.setAttribute('src', '/img/up.png')
            approval.setAttribute('class', 'ratingImg up-rating')
            const disapproval = document.createElement('img')
            disapproval.setAttribute('src', '/img/down.png')
            disapproval.setAttribute('class', 'ratingImg down-rating')
            const approvalA = document.createElement('a')
            approvalA.setAttribute('onclick', 'changeRating(this, 0, ' + i + ')')
            approvalA.append(approval)
            const disapprovalA = document.createElement('a')
            disapprovalA.setAttribute('onclick', 'changeRating(this, 1, ' + i +')')
            disapprovalA.append(disapproval)
            const approvalcount = document.createElement('h5')
            approvalcount.innerText = "0"
            const disapprovalcount = document.createElement('h5')
            disapprovalcount.innerText = "0"
            const approvalMemberList = document.createElement('h4') //THIS WILL BE A COLLAPSABLE ELEMENT
            approvalMemberList.innerText = "list of approval members"
            const disapprovalMemberList = document.createElement('h4') //THIS WILL BE A COLLAPSABLE ELEMENT
            disapprovalMemberList.innerText = "list of disapproval members"
            const divrow = document.createElement('div')
            divrow.setAttribute('class', 'row')
            const divcol1 = document.createElement('div')
            divcol1.setAttribute('class', 'col-sm-6')
            divcol1.append(approvalA)
            divcol1.append(approvalcount)
            divcol1.append (approvalMemberList)
            divrow.append(divcol1)
            const divcol2 = document.createElement('div')
            divcol2.setAttribute('class', 'col-sm-6')
            divcol2.append(disapprovalA)
            divcol2.append(disapprovalcount)
            divcol2.append(disapprovalMemberList)
            divrow.append(divcol2)
            cinners[i].append(h1)
            cinners[i].append(h4)
            cinners[i].append(divrow)
			cinners[i].append(h6)

        }
    for(let i=num; i<5; i++) {
        cindicators[i].remove()
        cinners[i].remove()
    }
}
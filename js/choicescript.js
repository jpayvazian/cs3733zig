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
			const aApproves = []
			const aDisapproves = []
			//TODO: add support for feedback.
			let number = 0
			for(const alt in json.choice.alternatives) { //is this right syntax?
				if(json.choice.alternatives[alt]==null) break
				aNames.push(json.choice.alternatives[alt].name)
				aDescrips.push(json.choice.alternatives[alt].description)
				aIds.push(json.choice.alternatives[alt].id)
				aApproves.push(json.choice.alternatives[alt].approvers)
				aDisapproves.push(json.choice.alternatives[alt].disapprovers)
				number += 1
			}
			createCarousel(aNames, aDescrips, aIds, aApproves, aDisapproves, number)
			document.querySelector('#code').innerText = json.choice.id
		} else {
			console.log("ERROR")
		}		
	})
}

function changeRating(self, strNum, otherInt) {
        const strs = ["up", "down"]
		const memberName = localStorage.getItem('memberName')
		const rating = self.childNodes[0].src.includes('up')
		const idAlternative = document.querySelectorAll('.idAlternative')[otherInt].innerText
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
				document.querySelectorAll('.upcount')[otherInt].innerText=json.approvers.length
				document.querySelectorAll('.downcount')[otherInt].innerText=json.disapprovers.length
				const aNames = document.querySelectorAll('.approveul')[otherInt]
				while (aNames.firstChild) {
				    aNames.removeChild(aNames.lastChild)
				  }
				for(let i=0; i<json.approvers.length; i++) {
					const li = document.createElement('li')
					li.innerText=json.approvers[i]
					aNames.append(li)
				}
				const dNames = document.querySelectorAll('.disapproveul')[otherInt]
				while (dNames.firstChild) {
				    dNames.removeChild(dNames.lastChild)
				  }
				for(let i=0; i<json.disapprovers.length; i++) {
					const li = document.createElement('li')
					li.innerText=json.disapprovers[i]
					dNames.append(li)
				}
		        //in here, also make sure that it deselects the other one!
			} else {
				console.log("ERROR!")
			}
		})

		
    }
function createCarousel(aNames, aDescrips, aIds, aApproves, aDisapproves, num) {
    const cindicators = document.querySelectorAll('.cli')
    const cinners = document.querySelectorAll('.carousel-item')
    for(let i=0; i<num; i++) {
            const h1 = document.createElement('h1')
            h1.innerText=aNames[i]
            const h4 = document.createElement('h4')
            h4.innerText=aDescrips[i]            
			const h6 = document.createElement('h6')
			h6.setAttribute('class', 'idAlternative')
			h6.innerText=aIds[i]
            const approval = document.createElement('img')
			if(aApproves[i].includes(localStorage.getItem('memberName'))) {
				approval.setAttribute('src', '/img/blueup.png')
			} else {
				approval.setAttribute('src', '/img/up.png')
			}
            
            approval.setAttribute('class', 'ratingImg up-rating')
            const disapproval = document.createElement('img')
            if(aDisapproves[i].includes(localStorage.getItem('memberName'))) {
				disapproval.setAttribute('src', '/img/bluedown.png')
			} else {
				disapproval.setAttribute('src', '/img/down.png')
			}
            disapproval.setAttribute('class', 'ratingImg down-rating')
            const approvalA = document.createElement('a')
            approvalA.setAttribute('onclick', 'changeRating(this, 0, ' + i + ')')
            approvalA.append(approval)
            const disapprovalA = document.createElement('a')
            disapprovalA.setAttribute('onclick', 'changeRating(this, 1, ' + i +')')
            disapprovalA.append(disapproval)
            const approvalcount = document.createElement('h5')
            approvalcount.innerText = aApproves[i].length
			approvalcount.setAttribute('class', 'upcount')
            const disapprovalcount = document.createElement('h5')
            disapprovalcount.innerText = aDisapproves[i].length
			disapprovalcount.setAttribute('class', 'downcount')
           const approvalMemberList = document.createElement('div') //THIS WILL BE A COLLAPSABLE ELEMENT
            const approveButton = document.createElement('button')
            approveButton.setAttribute('class', 'btn')
            approveButton.setAttribute('class', 'btn-primary')
            approveButton.setAttribute('type', 'button')
            approveButton.setAttribute('data-toggle', 'collapse')
            approveButton.setAttribute('data-target', `#approvecollapse${i}`)
            approveButton.innerText="List Of Approved Peeps"
            approvalMemberList.append(approveButton)
            const approveDiv = document.createElement('div')
            approveDiv.setAttribute('class', 'collapse')
            approveDiv.setAttribute('id', `approvecollapse${i}`)
            const ul = document.createElement('ul')
			ul.setAttribute('class', 'approveul')
            for(let j=0; j<aApproves[i].length; j++) {
            	const li = document.createElement('li')
            	li.innerText=aApproves[i][j]
            	ul.append(li)
            }
            approveDiv.append(ul)
            approvalMemberList.append(approveDiv)
            const disapprovalMemberList = document.createElement('div') //THIS WILL BE A COLLAPSABLE ELEMENT
            const disapproveButton = document.createElement('button')
            disapproveButton.setAttribute('class', 'btn')
            disapproveButton.setAttribute('class', 'btn-primary')
            disapproveButton.setAttribute('type', 'button')
            disapproveButton.setAttribute('data-toggle', 'collapse')
            disapproveButton.setAttribute('data-target', `#disapprovecollapse${i}`)
            disapproveButton.innerText="List Of Disapproved Peeps"
            disapprovalMemberList.append(disapproveButton)
			const disapproveDiv = document.createElement('div')
            disapproveDiv.setAttribute('class', 'collapse')
            disapproveDiv.setAttribute('id', `disapprovecollapse${i}`)
            const disul = document.createElement('ul')
			disul.setAttribute('class', 'disapproveul')
            for(let j=0; j<aDisapproves[i].length; j++) {
            	const li = document.createElement('li')
            	li.innerText=aDisapproves[i][j]
            	disul.append(li)
            }
            disapproveDiv.append(disul)
            disapprovalMemberList.append(disapproveDiv)
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
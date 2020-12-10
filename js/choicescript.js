const url = 'https://zxnfjm0fbk.execute-api.us-east-2.amazonaws.com/beta1' //modify when we change API
window.onload = () => {
	const idChoice = window.location.search.substring(window.location.search.indexOf("?idchoice=")+10, window.location.search.indexOf("?idchoice=")+46);
	console.log(idChoice);
	document.querySelector('#mName').innerText += " " + localStorage.getItem('memberName')
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
			const aFeedbacks = []
			let number = 0
			for(const alt in json.choice.alternatives) { //is this right syntax?
				if(json.choice.alternatives[alt]==null) break
				aNames.push(json.choice.alternatives[alt].name)
				aDescrips.push(json.choice.alternatives[alt].description)
				aIds.push(json.choice.alternatives[alt].id)
				aApproves.push(json.choice.alternatives[alt].approvers)
				aDisapproves.push(json.choice.alternatives[alt].disapprovers)
				aFeedbacks.push(json.choice.alternatives[alt].feedback)
				number += 1
			}
			createCarousel(aNames, aDescrips, aIds, aApproves, aDisapproves, number, aFeedbacks)
            document.querySelector('#code').innerText = json.choice.id
            if(json.choice.completed) {
                completeChoice(json.choice.chosenAlternative.name)
            }
		} else {
			console.log("ERROR")
		}		
	})
}

function changeRating(self, strNum, otherInt) {
    const strs = ["up", "down"]
    let src = null;
    for(let i=0; i<self.childNodes.length; i++) {
        if(self.childNodes[i].src!=null) src=self.childNodes[i]
    }
    const memberName = localStorage.getItem('memberName')
    const rating = src.src.includes('up')
    const idAlternative = document.querySelectorAll('.idAlt')[otherInt].innerText
    fetch(url+"/addRating", {
        method:'POST',
        body:JSON.stringify({rating, memberName, idAlternative})
    })
    .then(response=>response.json())
    .then(json=>{
        console.log(json)
        if(json.statusCode==200) {
            //below only happens if successful
            if(src.src.includes('blue')) {
                src.src=`/img/${strs[strNum]}.png`
            } else {
                src.src=`/img/blue${strs[strNum]}.png`
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

function showFeedbackAddForm(altNum) {
    const theDiv = document.querySelectorAll('.feedbackForm')[altNum]
    if(theDiv.childNodes.length==0) {
        const field = document.createElement('textarea')
        field.setAttribute('id', `feedbackTextArea${altNum}`)
        const submitButton = document.createElement('button')
        submitButton.setAttribute('class', 'btn')
        submitButton.setAttribute('class', 'btn-primary')
        submitButton.innerText = 'Submit'
        submitButton.setAttribute('onclick', `addFeedback(${altNum})`)
        theDiv.append(field)
        theDiv.append(submitButton)
    }
}

function pickAlternative(altNum) {
    const idAlternative = document.querySelectorAll('.idAlt')[altNum].innerText
    const idChoice = document.querySelector('#code').innerText
    fetch(url+'/completeChoice', {
        method:'POST',
        body:JSON.stringify({idAlternative, idChoice})
    })
    .then(response=>response.json())
    .then(json=>{
        if(json.statusCode==200) {
            alert("Choice is completed! You may no longer modify this choice.")
            completeChoice(document.querySelectorAll('.altName')[altNum].innerText)
        } else {
            console.log("ERROR COMPLETING CHOICE")
        }
    })   
    console.log('YOU HAVE PICKED ALTERNATIVE ' + altNum)

}

function completeChoice(alt) {
    const end = document.createElement('h2')
    end.innerText="CHOICE IS COMPLETED."
    const chosenAlt = document.createElement('h3')
    chosenAlt.innerText = alt
    document.querySelector('.jumbotron').append(end)
    document.querySelector('.jumbotron').append(chosenAlt)
    Array.prototype.slice.call(document.querySelectorAll("a")).map(i=>i.onclick='')
    Array.prototype.slice.call(document.querySelectorAll(".onlyForActive")).map(i=>i.remove())
}

function addFeedback(altNum) {
    const feedback = document.querySelector(`#feedbackTextArea${altNum}`).value
    if(feedback=='') {
        alert('ERROR: empty text field!')
    } else {
        const idAlternative = document.querySelectorAll('.idAlt')[altNum].innerText
        fetch(url+'/addFeedback', {
            method:'POST',
            body:JSON.stringify({memberName:localStorage.getItem('memberName'), contents:feedback, idAlternative})
        })
        .then(response=>response.json())
        .then(json=>{
            console.log(json);
            if(json.statusCode==200) {
                console.log(json.feedback)
                const feedbackP = document.createElement('p')
                feedbackP.innerText = json.feedback.contents
                const feedbackMN = document.createElement('h4')
                feedbackMN.innerText = 'By ' + json.feedback.memberName
                feedbackMN.style.marginRight='40px'
                feedbackMN.style.fontStyle = 'italic'
				const timestamp = document.createElement('h6')
				timestamp.innerText = new Date(json.feedback.timestamp)
                const hr = document.createElement('hr')
                document.querySelector('#feedbackcollapse'+altNum).append(feedbackP)
                document.querySelector('#feedbackcollapse'+altNum).append(feedbackMN)
				document.querySelector('#feedbackcollapse'+altNum).append(timestamp)
                document.querySelector('#feedbackcollapse'+altNum).append(hr)
                alert('Feedback added!')
                const f = document.querySelectorAll('.feedbackForm')[altNum]
                while (f.firstChild) {
                    f.removeChild(f.lastChild)
                }
            } else {
                console.log("ERROR ADDING FEEDBACK!")
            }
        })
    }
}

function createCarousel(aNames, aDescrips, aIds, aApproves, aDisapproves, num, aFeedbacks) {
    const cindicators = document.querySelectorAll('.cli')
    const cinners = document.querySelectorAll('.carousel-item')
    for(let i=0; i<num; i++) {
            document.querySelectorAll('.altName')[i].innerText=aNames[i]
            document.querySelectorAll('.altDes')[i].innerText=aDescrips[i]            
			document.querySelectorAll('.idAlt')[i].innerText=aIds[i]
            
			if(aApproves[i].includes(localStorage.getItem('memberName'))) {
				document.querySelectorAll('.up-rating')[i].setAttribute('src', '/img/blueup.png')
			} else {
				document.querySelectorAll('.up-rating')[i].setAttribute('src', '/img/up.png')
			}
            if(aDisapproves[i].includes(localStorage.getItem('memberName'))) {
				document.querySelectorAll('.down-rating')[i].setAttribute('src', '/img/bluedown.png')
			} else {
				document.querySelectorAll('.down-rating')[i].setAttribute('src', '/img/down.png')
			}
            document.querySelectorAll('.upcount')[i].innerText = aApproves[i].length
            document.querySelectorAll('.downcount')[i].innerText = aDisapproves[i].length
            //list of upvoted users
            const aul = document.querySelectorAll('.approveul')[i]
            for(let j=0; j<aApproves[i].length; j++) {
            	const li = document.createElement('li')
            	li.innerText=aApproves[i][j]
            	aul.append(li)
            }
            //list of downvoted users
            const disul = document.querySelectorAll('.disapproveul')[i]
            for(let j=0; j<aDisapproves[i].length; j++) {
            	const li = document.createElement('li')
            	li.innerText=aDisapproves[i][j]
            	disul.append(li)
            }
            
			//NEW CODE (FEEDBACK STUFF)

			if(aFeedbacks[i]!=null) {
	            const divOfFeedbacks = document.querySelectorAll('.feedbacks')[i]
	            //TODO: add for loop to go through feedback for alternative, adding it.
	            for(let x=0; x<aFeedbacks[i].length; x++) {
	            	const desc = document.createElement('p')
	            	desc.innerText = aFeedbacks[i][x].contents
	            	const nme = document.createElement('h4')
	            	nme.innerText = 'By ' + aFeedbacks[i][x].memberName
	            	nme.style.marginRight='40px'
	            	nme.style.fontStyle = 'italic'
					const timestamp = document.createElement('h6')
					timestamp.innerText = new Date(aFeedbacks[i][x].timestamp)
	            	const hrLine = document.createElement('hr')
	            	divOfFeedbacks.append(desc)
	            	divOfFeedbacks.append(nme)
					divOfFeedbacks.append(timestamp)
	            	divOfFeedbacks.append(hrLine)
	            }
	            cinners[i].append(divOfFeedbacks)
			}

			
        }
    for(let i=num; i<5; i++) {
        cindicators[i].remove()
        cinners[i].remove()
    }
}
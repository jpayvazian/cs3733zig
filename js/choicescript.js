const url = 'https://zxnfjm0fbk.execute-api.us-east-2.amazonaws.com/beta1' //modify when we change API
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
			const aFeedbacks = []
			//TODO: add support for feedback.
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

    function addFeedback(altNum) {
    	const feedback = document.querySelector(`#feedbackTextArea${altNum}`).value
    	if(feedback=='') {
    		alert('ERROR: empty text field!')
    	} else {
			const idAlternative = document.querySelectorAll('.idAlternative')[altNum].innerText
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
					const hr = document.createElement('hr')
					document.querySelector('#feedbackcollapse'+altNum).append(feedbackP)
					document.querySelector('#feedbackcollapse'+altNum).append(feedbackMN)
					document.querySelector('#feedbackcollapse'+altNum).append(hr)
				} else {
					console.log("ERROR ADDING FEEDBACK!")
				}
			})
    	}
}
    	//TODO: make the fetch for addign zee feedback!

function createCarousel(aNames, aDescrips, aIds, aApproves, aDisapproves, num, aFeedbacks) {
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
			//NEW CODE (FEEDBACK STUFF)
			const hr = document.createElement('hr')
            cinners[i].append(hr)
			const addFeedbackBtn = document.createElement('button')
            addFeedbackBtn.innerText = "Add Feedback"
            addFeedbackBtn.setAttribute('onclick', `showFeedbackAddForm(${i})`)
            addFeedbackBtn.style.cssFloat = 'left'
            addFeedbackBtn.style.margin = '10px'
            addFeedbackBtn.style.marginLeft = '180px'
            addFeedbackBtn.setAttribute('class', 'btn')
            addFeedbackBtn.setAttribute('class', 'btn-secondary')
            cinners[i].append(addFeedbackBtn)
            const chooseAltBtn = document.createElement('button')
            chooseAltBtn.innerText = 'Choose This Alt'
            chooseAltBtn.setAttribute('onclick', `pickAlternative(${i})`)
            chooseAltBtn.style.cssFloat = 'right'
            chooseAltBtn.style.margin = '10px'
            chooseAltBtn.style.marginRight = '180px'
            chooseAltBtn.setAttribute('class', 'btn')
            chooseAltBtn.setAttribute('class', 'btn-secondary')
            cinners[i].append(chooseAltBtn) 
            const feedbackFormDiv = document.createElement('div')
            feedbackFormDiv.setAttribute('class', `feedbackForm`)
            cinners[i].append(feedbackFormDiv)
            const hr2 = document.createElement('hr')
            cinners[i].append(hr2)

			if(aFeedbacks[i]!=null) {
				
			
	            const feedLabel = document.createElement('h2')
	            feedLabel.innerText='FEEDBACK:'
	            cinners[i].append(feedLabel)
	            const divOfFeedbacks = document.createElement('div')
	            divOfFeedbacks.setAttribute('class', 'collapse')
	            divOfFeedbacks.setAttribute('id', `feedbackcollapse${i}`)
	            const showFeedbacks = document.createElement('button')
	            showFeedbacks.setAttribute('class', 'btn')
	            showFeedbacks.setAttribute('class', 'btn-primary')
	            showFeedbacks.setAttribute('type', 'button')
	            showFeedbacks.setAttribute('data-toggle', 'collapse')
	            showFeedbacks.setAttribute('data-target', `#feedbackcollapse${i}`)
	            showFeedbacks.innerText = 'Show Feedbacks'
	            cinners[i].append(showFeedbacks)
	            //TODO: add for loop to go through feedback for alternative, adding it.
	            for(let x=0; x<aFeedbacks[i].length; x++) {
	            	const desc = document.createElement('p')
	            	desc.innerText = aFeedbacks[i][x].contents
	            	const nme = document.createElement('h4')
	            	nme.innerText = 'By ' + aFeedbacks[i][x].memberName
	            	nme.style.marginRight='40px'
	            	nme.style.fontStyle = 'italic'
	            	const hrLine = document.createElement('hr')
	            	divOfFeedbacks.append(desc)
	            	divOfFeedbacks.append(nme)
	            	divOfFeedbacks.append(hrLine)
	            }
	            cinners[i].append(divOfFeedbacks)
			}


            for(let k=0; k<5; k++) {
            	const br = document.createElement('br')
            	cinners[i].append(br)
            }
			
        }
    for(let i=num; i<5; i++) {
        cindicators[i].remove()
        cinners[i].remove()
    }
}
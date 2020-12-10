const url = 'https://zxnfjm0fbk.execute-api.us-east-2.amazonaws.com/beta1'
window.onload = () => {
    document.querySelector('#reportchoices').onclick = e=> {
        e.preventDefault()
        const reportedChoices = document.getElementById('listOfChoicesReport')
	
        fetch(url+'/choice', {
            method:'GET'
        })
        .then( response => response.json())
        .then(json=> {
            console.log(json)
			
			const choices = json.choices
			if(json.statusCode==200){
				while(reportedChoices.firstChild){
					reportedChoices.removeChild(reportedChoices.firstChild)
				}
				const table = document.createElement('table')
				table.setAttribute('class', 'table')
				
				const tableHead = document.createElement('thead')
				const columnHeads = document.createElement('tr')
				const choiceID = document.createElement('th')
				choiceID.setAttribute('scope', 'col')
				choiceID.innerText = "Choice ID"
				columnHeads.appendChild(choiceID)
				const choiceDescription = document.createElement('th')
				choiceDescription.setAttribute('scope', 'col')
				choiceDescription.innerText = "Choice Description"
				columnHeads.appendChild(choiceDescription)
				const doc = document.createElement('th')
				doc.setAttribute('scope', 'col')
				doc.innerText = "Date Of Creation"
				columnHeads.appendChild(doc)
				const isCompleted = document.createElement('th')
				isCompleted.setAttribute('scope', 'col')
				isCompleted.innerText = "isCompleted"
				columnHeads.appendChild(isCompleted)
				tableHead.appendChild(columnHeads)
				table.appendChild(tableHead)
					
				const tableBody = document.createElement('tbody')
				
				for(let i = 0; i<choices.length; i++){
					const tableRow = document.createElement('tr')
					
					const rowID = document.createElement('th')
					rowID.setAttribute('scope', 'row')
					rowID.innerText = choices[i].id
					tableRow.appendChild(rowID)
					
					const rowDescription = document.createElement('td')
					rowDescription.innerText = choices[i].description
					tableRow.appendChild(rowDescription)
					
					var date = new Date (choices[i].startDate);
					const rowDate = document.createElement('td')
					rowDate.innerText = date
					tableRow.appendChild(rowDate)
					
					const rowComplete = document.createElement('td')
					rowComplete.innerText = choices[i].completed
					tableRow.appendChild(rowComplete)
					
					tableBody.appendChild(tableRow)
				}
				
				table.appendChild(tableBody)
				reportedChoices.appendChild(table)
				
			}
			else alert("Error!")
        })
    }

	document.querySelector('#deletechoices').onclick = e=> {
        e.preventDefault()
	
        fetch(url+'/deleteChoices', {
            method:'POST',
			body:JSON.stringify({days:document.getElementById('numDays').value})
        })
        .then( response => response.json())
        .then(json=> {
            console.log(json)
			
			if(json.statusCode==200){
				alert(json.nmbrDeleted + " Choices Deleted")
			}
			else alert("Error!")
        })
    }
    
}
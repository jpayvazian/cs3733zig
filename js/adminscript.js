const url = 'https://zxnfjm0fbk.execute-api.us-east-2.amazonaws.com/alpha4'
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
			let output = ""
			if(json.statusCode==200){
				$( "#listOfChoicesReport" ).empty();
				for(let i = 0; i<choices.length; i++){
					var date = new Date (choices[i].startDate);
					
					const choiceElement = document.createElement('div')
					choiceElement.setAttribute('class','choiceElement')
					const h3 = document.createElement('h3')
					h3.innerText = choices[i].id + ": " + "\n"
					const info = document.createElement('p')
					info.innerText = "DATE OF CREATION: " + date + ", " + "COMPLETED: " + choices[i].completed
					const horizontal = document.createElement('hr')
					choiceElement.append(h3)
					choiceElement.append(info)
					choiceElement.append(horizontal)
					
					reportedChoices.append(choiceElement)
				}
				
				
			}
			else alert("Error!")
        })
    }
    
}
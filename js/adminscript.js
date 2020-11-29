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
				for(let i = 0; i<choices.length; i++){
					var date = new Date (choices[i].startDate);
					output = output + "<h3>" + choices[i].id + ": " + "</h3><br>"
					output = output + "DATE OF CREATION: " + date + ", " + "COMPLETED: " + choices[i].completed + "<hr>"
				}
				
				reportedChoices.innerHTML = output
			}
			else alert("Error!")
        })
    }
    
}
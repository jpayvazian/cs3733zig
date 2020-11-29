const url = 'https://zxnfjm0fbk.execute-api.us-east-2.amazonaws.com/alpha4'
window.onload = () => {
    document.querySelector('#reportchoices').onclick = e=> {
        e.preventDefault()
        const reportedChoices = document.querySelectorAll('.reportedchoices')
	
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
					output = output + "<h2>" + choices[i].id + "</h2><br>"
					output = output + "DATE OF CREATION: " + choices[i].startDate + ", " + "COMPLETED: " + choices[i].completed + "<br>"
				}
				
				reportedChoices.innerHTML = output
			}
			else alert("Error!")
        })
    }
    
}
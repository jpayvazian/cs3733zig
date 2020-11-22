const url = 'https://zxnfjm0fbk.execute-api.us-east-2.amazonaws.com/alpha1' //modify when we change API
window.onload = () => {
	const idChoice = window.location.search.substring(window.location.search.indexOf("?idchoice=")+10, window.location.search.indexOf("?idchoice=")+46);
	console.log(idChoice);
	fetch(url+"/choice/"+idChoice, {
		method:'GET',
		body:JSON.stringify({idChoice})
	})
	.then(response=>response.json())
	.then(json=>{
		if(json.statusCode==200) {
			document.querySelector('#choicedescription').innerText = json.choice.description
			for(alt in json.choice.alternatives) { //is this right syntax?
				const altEle = document.createElement('div')
				altEle.appendChild(document.createElement('h2').innerText=alt.name)
				altEle.appendChild(document.createElement('h3').innerText=alt.description)
				//FOR NOW, we don't need to worry about feedback/rating. let's just try and get this done first :)
				document.querySelector('#alternative').appendChild(altEle)
			}
		} else {
			console.log("ERROR")
		}		
	})
}
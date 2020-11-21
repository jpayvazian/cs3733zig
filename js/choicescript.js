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
		console.log(json)
		//THIS IS WHERE WE POPULATE JSON SHIT
	})
}
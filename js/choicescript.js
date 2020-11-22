const url = 'https://zxnfjm0fbk.execute-api.us-east-2.amazonaws.com/alpha3' //modify when we change API
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
			for(const alt in json.choice.alternatives) { //is this right syntax?
				const altEle = document.createElement('div')
				const h2 = document.createElement('h2')
				h2.innerText = json.choice.alternatives[alt].name
				altEle.append(h2)
				const h3 = document.createElement('h3')
				h3.innerText = json.choice.alternatives[alt].description
				altEle.append(h3)
				//FOR NOW, we don't need to worry about feedback/rating. let's just try and get this done first :)
				document.querySelector('#alternatives').append(altEle)
			}
		} else {
			console.log("ERROR")
		}		
	})
}
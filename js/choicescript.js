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
			const aNames = []
			const aDescrips = []
			let number = 0
			for(const alt in json.choice.alternatives) { //is this right syntax?
				if(json.choice.alternatives[alt]==null) break
				aNames.push(json.choice.alternatives[alt].name)
				aDescrips.push(json.choice.alternatives[alt].description)
				number += 1
			}
			createCarousel(aNames, aDescrips, number)
			document.querySelector('#code').innerText = json.choice.id
		} else {
			console.log("ERROR")
		}		
	})
}

function createCarousel(aNames, aDescrips, num) {
    const cindicators = document.querySelectorAll('.cli')
    const cinners = document.querySelectorAll('.carousel-item')
    for(let i=0; i<num; i++) {
        const h1 = document.createElement('h1')
        h1.innerText=aNames[i]
        const h4 = document.createElement('h4')
        h4.innerText=aDescrips[i]
        cinners[i].append(h1)
        cinners[i].append(h4)
    }
    for(let i=num; i<5; i++) {
        cindicators[i].remove()
        cinners[i].remove()
    }
}
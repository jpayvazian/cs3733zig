const url = 'https://zxnfjm0fbk.execute-api.us-east-2.amazonaws.com/alpha3'
window.onload = () => {
    document.querySelector('#createChoice').onclick = e=> {
        e.preventDefault()
        const inputs = document.querySelectorAll('.choiceField')
        const alternatives = createAlternatives()
		const alternativeNames = alternatives.alternativeNames
		const alternativeDescriptions = alternatives.alternativeDescriptions
        console.log({description:inputs[0].value,numMembers:parseInt(inputs[1].value), alternativeNames, alternativeDescriptions})
        fetch(url+'/createChoice', {
            method:'POST',
            body:JSON.stringify({description:inputs[0].value,numMembers:parseInt(inputs[1].value), alternativeNames, alternativeDescriptions})
        })
        .then( response => response.json())
        .then(json=> {
            console.log(json)
            //this is where we can let the client know if there are errors.
            //if there are not errors, we let client know the choice id!
        })
    }
    document.querySelector('#createChoice').disabled = true
    Array.prototype.slice.call(document.querySelectorAll(".choiceField")).map(i=>i.onblur=()=> {
        const names = document.querySelectorAll(".altname")
        const descriptions = document.querySelectorAll(".altdescription")
        let count = 0;
        for(let i=0; i<5; i++) {
            count += names[i].value!=""&&descriptions[i].value!=""
        }
        document.querySelector('#createChoice').disabled = count < 2 || document.querySelector('#choicedescription').value=='' || document.querySelector('#numMembers').value<1
    })
    document.querySelector('#registerchoice').onclick = e => {
        e.preventDefault()
        //window.location.href = 'choice.html?c='+document.querySelector('#choicecode').value
        const inputs = document.querySelectorAll('.registerField')
        //NOTE: this should serve as both a log in and a sign up
        //basically, if user does not exist, we create one
        //if user exists, we log in as them
        //only time we error are:
        //(1) invalid code
        //(2) invalid password for created user
        fetch(url+'/registerForChoice', {
            method:'POST',
            body:JSON.stringify({memberName: inputs[1].value, password: inputs[2].value, idChoice: inputs[0].value})
        })
        .then( response => response.json())
        .then(json=> {
			if(json.statusCode==200) {
				window.location.href='choice.html?idchoice='+json.idChoice
			} else console.log("ERROR")
            //this is where we can let the client know if there are errors.
            //if there are not errors, we redirect client to choice page
        })
    }
    document.querySelector('#registerchoice').disabled = true;
    Array.prototype.slice.call(document.querySelectorAll(".registerField")).map(i=>i.onblur=()=>document.querySelector('#registerchoice').disabled = document.querySelector('#choicecode').value=="" || document.querySelector('#username').value=="")
    document.querySelector('#adminlogin').onclick = e => {
        e.preventDefault()
        window.location.href='admin.html'
    }
    
}

const createAlternatives = () => {
    const names = document.querySelectorAll(".altname")
    const descriptions = document.querySelectorAll(".altdescription")
    const alternativeNames = []
	const alternativeDescriptions = []
    for(let i=0; i<names.length; i++) {
        if(names[i].value!=""&&descriptions[i].value!="") {
            alternativeNames.push(names[i].value)
			alternativeDescriptions.push(descriptions[i].value)
        }
    }
    return {alternativeNames, alternativeDescriptions}
}
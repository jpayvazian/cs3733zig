window.onload = () => {
    document.querySelector('#createChoice').onclick = function(e) {
        e.preventDefault()
        const inputs = document.querySelectorAll('.choiceField')
        const alternatives = createAlternatives()
        console.log(alternatives)
        fetch('/createChoice', {
            method:'POST',
            body:JSON.stringify({description:inputs[0].value,numPeople:inputs[1].value, alternatives}),
            headers: {
                'Content-Type':'application/json'
            }
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
            count += (names[i].value!=""&&descriptions[i].value!="")?1:0
        }
        document.querySelector('#createChoice').disabled = count < 2 || document.querySelector('#choicedescription').value=='' || document.querySelector('#numMembers').value<1
    })
    document.querySelector('#registerchoice').onclick = function(e) {
        e.preventDefault()
        //window.location.href = 'choice.html?c='+document.querySelector('#choicecode').value
        const inputs = document.querySelectorAll('.registerFields')
        //NOTE: this should serve as both a log in and a sign up
        //basically, if user does not exist, we create one
        //if user exists, we log in as them
        //only time we error are:
        //(1) invalid code
        //(2) invalid password for created user
        fetch('/registerChoice', {
            method:'POST',
            body:JSON.stringify({code: inputs[0].value, username: inputs[1].value, password: inputs[2].value}),
            headers: {
                'Content-Type':'application/json'
            }
        })
        .then( response => response.json())
        .then(json=> {
            console.log(json)
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
    const alternatives = []
    for(let i=0; i<names.length; i++) {
        if(names[i].value!=""&&descriptions[i].value!="") {
            alternatives.push({name: names[i].value, description: descriptions[i].value})
        }
    }
    return alternatives
}
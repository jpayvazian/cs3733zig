const checkIfUserEnteredEverything = function() {
    const names = document.querySelectorAll(".altname")
    const descriptions = document.querySelectorAll(".altdescription")
    let count = 0;
    for(let i=0; i<5; i++) {
        count += (names[i].value!=""&&descriptions[i].value!="")?1:0
    }
    document.querySelector('#createChoice').disabled = count < 2 || document.querySelector('#choicedescription').value=='' || document.querySelector('#numMembers').value<1
}

window.onload = function() {
    document.querySelector('#createChoice').onclick = function(e) {
        
    }
    document.querySelector('#createChoice').disabled = true;
    Array.prototype.slice.call(document.querySelectorAll(".choiceField")).map(i=>i.onblur=checkIfUserEnteredEverything);
}
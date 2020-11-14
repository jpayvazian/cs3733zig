const checkIfUserEnteredEverything = function() {
    if(Array.prototype.slice.call(document.querySelectorAll(".choiceField")).filter(i=>i.value!="").length<6) {
        document.querySelector('#createChoice').disabled = true;
    } else {
        const arr = [0,0,0,0,0];
        let i = 0;
        let count = 0;
        Array.prototype.slice.call(document.querySelectorAll(".altname")).map(x=>arr[i++]=x.value!="");
        i=0;
        Array.prototype.slice.call(document.querySelectorAll(".altdescription")).forEach(x=>count+=(arr[i++]&&x.value!="")?1:0);
        document.querySelector('#createChoice').disabled = count < 2;
        console.log(count);
    }
}

window.onload = function() {
    document.querySelector('#createChoice').onclick = function(e) {
        
    }
    document.querySelector('#createChoice').disabled = true;
    Array.prototype.slice.call(document.querySelectorAll(".choiceField")).map(i=>i.onblur=checkIfUserEnteredEverything);
}
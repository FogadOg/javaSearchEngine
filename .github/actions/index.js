
fetch(".github/myText.txt")
.then((res) => res.text())
.then((text) => {
    text.replace("color", "colour");
    
 })
.catch((e) => console.error(e));


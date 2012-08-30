/********************************************
*	Generer une popup				        *
*	et creer un masque sur la page courante	*
*   Non complet e terminer					*
*********************************************/

/*
* PAS UTILISE
** LE MASK FONCTIONNE IL RESTE A CREER LA POPUP
** TEST EFFECTUE LE 16/06/2008
**/

/*Variables globales*/
var mask = document.createElement("div");

/**
* cree un masque e masque
* est associes a la classe CSS window_div_mask
*/
function createMask(txt){
	//recupere la dimension de la page
	var width = 0;
	var height = 0;
	var heightInner = 0;
	alert("san creatMask");
	if(document.body) { 
	    width = (document.body.scrollWidth);
	    alert("dans body" + width);
	} else if (window.innerWidth) {
		width = window.innerWidth;
		alert("dans window" + width);
	} 
	
	/*else if(document.getElementsByTagName("body")[0] 
		&& document.getElementsByTagName("body")[0].offsetWidth) {
	    width = document.getElementsByTagName("body")[0].offsetWidth;
	} */           
	
	if(document.body) { 
	    height = (document.body.scrollHeight );
	    alert("dans body" + height);
	} 
	if(document.getElementsByTagName("body")[0] 
			&& document.getElementsByTagName("body")[0].offsetHeight) {
	    heightInner = document.getElementsByTagName("body")[0].offsetHeight;
	    alert("dans window" + height);
	} 
	
	if (height < heightInner) { height = heightInner; }
	
	/*else if(document.getElementsByTagName("body")[0] 
			&& document.getElementsByTagName("body")[0].offsetHeight) { 
	    height = document.getElementsByTagName("body")[0].offsetHeight;
	}*/
		
	//cree le mask	
	/*var mask = document.createElement("div");*/
	mask.className = "window_div-mask";
	mask.style.width = width+"px";
	mask.style.height = height+"px";
	document.getElementsByTagName("body")[0].appendChild(mask);

}


/**
* cree un masque e masque
* est associes a la classe CSS window_div_mask
*/
function createPopup(txt){
	var popup = document.createElement("div");
	var text = document.createElement("span");	
	text.createTextNode(txt);
	//cree le mask	
	/*var mask = document.createElement("div");*/
	popup.className = "popup";
	popup.appendChild(text);
	document.getElementsByTagName("body")[0].appendChild(popup);

}



/*capte le clique et les bloques
Ne fonctionne pas sous IE et j'ai l'impression qu'on en a pas besoin.
mask.onclick = function(evn)
{
    evn = evn || window..event;

    if(evn.stopPropagation) evn.stopPropagation(); // DOM Level 2
    else evn.cancelBubble = true; // IE
    
    if(evn.preventDefault) evn.preventDefault();   // DOM Level 2
    else evn.returnValue = false; // IE
};
*/


/**
* HighLight input.
* @param formId : l'id du formulaire
*/
function highlightInputAndSelect(formId) {
	var NomNav = navigator.appName; // place le nom du navigateur dans la variable NomNav

    var form = document.getElementById(formId); 
    if (form == null) {
      if (debug) alert("form [" + formId + "] not found");
      return;
    }
    var inputs = form.getElementsByTagName("input");
    if (inputs == null) {
      if (debug) alert("no input found in the form [" + formId + "]");
      return;
    }
   
    // add event handlers so rows light up
    for (i = 0; i < inputs.length; i++) {
    	var input = inputs[i];
    	var previousClass = '';
    	var previousClassSubmit = '';
    	var type = input.type;
	    	if(type=='text'){
	    		input.onfocus = function() { 
	    			previousClass = this.className; this.className = 'form-input-field-focus'; 
			     };
			     input.onblur = function() {
			        	 this.className = previousClass; 
	    	   	};
	    	}
	    	else if (type=='submit') {
	    			var isLink = false;
	    			var splitClassName = input.className.split("-");
	    			for (var j = 0; j < splitClassName.length; ++j) {
	    				if (splitClassName[j] == 'link') {
	    					isLink = true;
	    				}
	    			}
	    			if (isLink) {
	    				input.onmouseover = function() { 
	    					previousClassSubmit = this.className; this.className += '-hover'; 
							
						 };
						 input.onmouseout = function() {
						    	 this.className = previousClassSubmit; 
									
				    	 };
	    			}
	    	}
	    
    }
    if (NomNav != "Microsoft Internet Explorer") {//on ne fait pas pour IE car bug
	    var selects = form.getElementsByTagName("select");
	    if (selects == null) {
	      if (debug) alert("no selects found in the form [" + formId + "]");
	      return;
	    }
	    // add event handlers so rows light up
	    for (i = 0; i < selects.length; i++) {
		        selects[i].onfocus = function() { previousClass = this.className; this.className = 'select_focus'; };
	    	    selects[i].onblur = function() { this.className = previousClass; };
	    }
	}
	
}






/**
 * Check all checkBox a row based the attribute checked.
 * @param dataTableId
 * @param indexRow
 * @param checked
 */
function checkRows(dataTableId, indexRow, checked) {
	var dataTable = document.getElementById(dataTableId); 
    if (dataTable == null) {
      if (debug) alert("table [" + dataTableId + "] not found");
      return;
    }
    var rows = dataTable.getElementsByTagName("tr");
    if (rows == null) {
      if (debug) alert("no rows found in the dataTable [" + dataTableId + "]");
      return;
    }
    //car il existe un ligne header qui n'est pas compte dans l'indexage JSF
    var row = rows[indexRow + 1];
    var inputs = row.getElementsByTagName("input");
    if (inputs == null) {
      if (debug) alert("no inputs found in the row.");
      return;
    }
    for (i = 0; i < inputs.length; i++) {
    	if (inputs[i].getAttribute("type") == "checkbox") {
    		if (checked) {
				inputs[i].value='true';
	    		inputs[i].checked = 'checked';
    		} else {
    			inputs[i].value='false';
				inputs[i].checked = false;
    		}
    	}
    }
    
}


/**
 * Check all checkBox a row based the attribute checked.
 * @param dataTableId
 * @param indexRow
 * @param checked
 */
function checkAllInElement(elementId, checked) {
	var element = document.getElementById(elementId); 
    if (element == null) {
      if (debug) alert("table [" + elementId + "] not found");
      return;
    }
    
    var inputs = element.getElementsByTagName("input");
    if (inputs == null) {
      if (debug) alert("no inputs found in this element" + elementId + ".");
      return;
    }
    for (i = 0; i < inputs.length; i++) {
    	if (inputs[i].getAttribute("type") == "checkbox") {
    		if (!inputs[i].disabled) {
	    		if (checked) {
					inputs[i].value='true';
		    		/*inputs[i].checked = 'checked';*/
		    		inputs[i].checked = true;
	    		} else {
	    			inputs[i].value='false';
					inputs[i].checked = false;
	    		}
    		}
    	}
    }
    
}


function changeAllMotivAvis(elementId, nameClass, selectElement) {
	var element = document.getElementById(elementId); 
    if (element == null) {
      if (debug) alert("table [" + elementId + "] not found");
      return;
    }
    
    var inputs = element.getElementsByTagName("select");
    if (inputs == null) {
      if (debug) alert("no selects found in this element" + elementId + ".");
      return;
    }
    
    if (selectElement == null) {
      if (debug) alert("no selects found in this element selectElement.");
      return;
    }
    
    var indexSelected = selectElement.selectedIndex;
    var defaultMotivAvis = selectElement.options[indexSelected].text;
    
    for (i = 0; i < inputs.length; i++) {
    	if (inputs[i].className == nameClass) {
    		if (!inputs[i].disabled) {
    			inputs[i].selectedIndex = indexSelected;
    		}
    	}
    }
    
}


/**
* Add the onblur event on the inputId that submit submitBt by simulate Click on button.
*/
function onblurInput(inputId, submitBt) {
    var input = document.getElementById(inputId); 
    if (input == null) {
      if (debug) alert("input [" + inputId + "] not found");
      return;
    }
   
    input.onblur = function() { simulateLinkClick(submitBt); };
}

/**
*Permet d'imprimer un div.
*@titre : Titre du doucument e imprimer
*@obj : id de l'objet e imprimer
*/
function imprimeZone(titre, obj) {
	// Definie la zone e imprimer
	var zi = document.getElementById(obj).innerHTML;

	// Ouvre une nouvelle fenetre
	var f = ouvrir('','',800, 650,0);
	
	// Ajoute les Donnees
	f.document.title = titre;
	f.document.header = document.header;
	f.document.body.innerHTML += " " + zi + " ";
	
	// Imprime et ferme la fenetre
	f.window.print();
	f.window.close();
	return true;
} 

/**
*ouvre une nouvelle fenetre centree.
*@param : url
*@param : fen
*@param : x
*@param : y
*@param : scrol
*@return window
*/
function ouvrir(url,fen,x,y,scrol) {
	var posX = (screen.availWidth - x) / 2;
	var posY = (screen.availHeight - y) / 2;
	
	if(scrol == 1){
		f = window.open(url,fen,"directories=no,top="+posY+",left="+posX+",height="+y+",width="+x+",location=no,toolbar=no,menubar=no,scrollbars=yes,resizable=no,status=no");
		return f;
	}else{
		f =  window.open(url,fen,"directories=no,top="+posY+",left="+posX+",height="+y+",width="+x+",location=no,toolbar=no,menubar=no,resizable=no,scrollbars=no,status=no");
		return f;
	}
	
}


/**
* Add the onblur event on the inputId that submit submitBt by simulate Click on button.
*/
function radioOnchange(input, choix, submitBt) {
    var val = input.value;
	/*alert("input : " + input + "  ;  choix : " + choix
    	+ "  ;  submitBt : " + submitBt + "  ;  val : " + val);*/
    if (val == choix) {
    	return;
    }
    
    simulateLinkClick(submitBt);
}

/**
 * Bloque la ctrl-c et ctrl-v
 * @return
 */
function disableCtrlKeyCombination(e)
{
	// list all CTRL + key combinations you want to disable
	var key;
	var isCtrl;

	if(window.event)
	{
		key = window.event.keyCode;     //IE
		if(window.event.ctrlKey)
			isCtrl = true;
		else
			isCtrl = false;
	}
	else
	{
		key = e.which;     //firefox
		if(e.ctrlKey)
			isCtrl = true;
		else
			isCtrl = false;
	}

	//if ctrl is pressed check if other key is in forbidenKeys array
	if(isCtrl)
	{
		//Touche c ou C enfonc�e
		if((key == 67) || (key == 99))
			return false;
		//Touche v ou V enfonc�e
		if((key == 86) || (key == 118))
			return false;	
	}
	return true;
}

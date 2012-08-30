/*--------------------------------------------------------------*
* Fonction qui controle les champs obligatoire 	        		*
* sont bien remplis. Ces contrele doivent etre aussi effectue	*
* cote java. 					                                *
*****************************************************************/
/**
*est passe e true s'il l'on veut envoyer le formulaire sans controle
*/
var clickAnnuler = false;
var debug = false;

/**
 * @param nameStyleClass //nom de la styleClass pour les labels des Champs Obligatoires
 * @param form the formulaire
 **/
function formValidator(form, nameStyleClass){
	//variable globle
	var classChpObligatoire = nameStyleClass;
	if(!clickAnnuler){
	    if (form == null) {
	      if (debug) alert("form [" + form.id + "] not found");
	      return;
	    }
	    
	      var selects = form.getElementsByTagName("select");
	    if (selects == null) {
	      if (debug) alert("no selects found in the form [" + form.id + "]");
	      return;
	    }
	   
	     var labels = form.getElementsByTagName("label");
	    if (labels == null) {
	      if (debug) alert("no label found in the form [" + form.id + "]");
	      return;
	    }
	    for (i = 0; i < labels.length; i++) {
			//variable
			var id_champs;
	     	var label = labels[i];
	     	var txt_label = label.firstChild.nodeValue;
			var class_label = label.className;
			if (class_label == classChpObligatoire) {//c'est un label d'un champ obligatoire
				if ( label.getAttribute("htmlFor") || label.getAttribute("for") ) {
					 if (document.all) {
						id_champs = label.getAttribute("htmlFor");
					}else{
						id_champs = label.getAttribute("for");
					}
				}
				var champs = document.getElementById(id_champs);
				if (champs == null) {
			      if (debug) alert("no champ of label found  [" + id_champs + "]");
				  
			    } else {
					if ( champs.tagName == "INPUT" && champs.getAttribute("type") == "text" ) {
						if ( champs.value == "" ){
							var txt = txt_label + " est vide ";
							alert(txt);
							/*createMask(txt);*/
							return false;
						};
					}else if(champs.tagName == "SELECT"){
						var select = champs;
						var currentValue = select.options[select.options.selectedIndex].value ;
						if ( currentValue == "" || currentValue == 0){
							alert(txt_label+" est vide");
							return false;
						}
					}else if ( champs.tagName == "TEXTAREA" && champs.value == "" ) {
						alert(txt_label+" est vide");
						return false;
					}
				}
				
			}
				
		}
		return;
	}
	return true;
}




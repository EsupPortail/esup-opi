//AJOUT POUR PALIER AU BUG DE NON SAISIE DE LA LETTRE L => IMPOSSIBLE CAR 
// c.NUMPAD_ENTER=108 en ASCII=L
PrimeFaces.widget.DefaultCommand = PrimeFaces.widget.BaseWidget.extend({
    init: function (b) {
        this.cfg = b;
        this.id = this.cfg.id;
        this.jqId = PrimeFaces.escapeClientId(this.id);
        this.jqTarget = $(PrimeFaces.escapeClientId(this.cfg.target));
        var a = this;
        this.jqTarget.parents("form:first").keypress(function (d) {
            var c = $.ui.keyCode;
            if (d.which == c.ENTER || d.which == c.NUMPAD_ENTER) {
                a.jqTarget.click();
                d.preventDefault()
            }
        });
        $(this.jqId + "_s").remove()
    }
});

PrimeFaces.locales ['fr'] = {
	    closeText: 'Fermer',
	    prevText: 'Précédent',
	    nextText: 'Suivant',
	    monthNames: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre' ],
	    monthNamesShort: ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Jun', 'Jul', 'Aoû', 'Sep', 'Oct', 'Nov', 'Déc' ],
	    dayNames: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'],
	    dayNamesShort: ['Dim', 'Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam'],
	    dayNamesMin: ['D', 'L', 'M', 'M', 'J', 'V', 'S'],
	    weekHeader: 'Semaine',
	    firstDay: 1,
	    isRTL: false,
	    showMonthAfterYear: false,
	    yearSuffix:'',
	    timeOnlyTitle: 'Choisir l\'heure',
	    timeText: 'Heure',
	    hourText: 'Heures',
	    minuteText: 'Minutes',
	    secondText: 'Secondes',
	    currentText: 'Maintenant',
	    ampm: false,
	    month: 'Mois',
	    week: 'Semaine',
	    day: 'Jour',
	    allDayText: 'Toute la journée'
	};

PrimeFaces.locales ['fr_FR'] = PrimeFaces.locales ['fr'];
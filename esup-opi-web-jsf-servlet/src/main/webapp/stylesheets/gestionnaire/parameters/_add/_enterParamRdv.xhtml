<%@include file="../../../_include/_include.jsp"%>
	<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi" styleClass="tableJustWidthMax">
		<e:text value="#{msgs['CALENDAR_RDV.TITLE.ENTER']}" styleClass="section-smallTitle"
			rendered="#{paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.addAction}"/>
		<e:text value="#{msgs['CALENDAR_RDV.TITLE.UPDATE']}" styleClass="section-smallTitle"
			rendered="#{paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.updateAction}">
			<f:param value="#{paramRdvController.calendarRDV.titre}"/>
		</e:text>
		<t:panelGroup>
			<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
				rendered="#{paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.addAction}"
				action="#{paramRdvController.add}"/>
			<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
				rendered="#{paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.updateAction}"
				action="#{paramRdvController.update}"/>
			<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
				action="#{paramRdvController.reset}"/>
		</t:panelGroup>
	</e:panelGrid>
	
	
	<t:panelGrid columns="2" styleClass="displayAddRdv" columnClasses="left,right">
		<e:outputLabel value="#{msgs['FIELD_LABEL.LIB_RDV']}" styleClass="formHeader" for="titre"
			rendered="#{paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.addAction}"/>
		<e:inputText id="titre" size="10" value="#{paramRdvController.calendarRDV.titre}"
			rendered="#{paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.addAction}"/>
			
		
		<e:outputLabel value="#{msgs['FIELD_LABEL.LIB_RDV']}" styleClass="formHeader" for="titreUpdate"
			rendered="#{paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.updateAction}"/>
		<e:text id="titreUpdate" value="#{paramRdvController.calendarRDV.titre}"
				rendered="#{paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.updateAction}"/>
		
		
		<e:outputLabel value="#{msgs['FIELD_LABEL.CHOIX_CGE_COMMISSION']}"
			styleClass="formHeader" for="choixCgeCommission"/>
		<h:selectOneRadio id="choixCgeCommission" immediate="true"
			onchange="submit();" styleClass="oneRadio"
			value="#{paramRdvController.choix}">
		    <f:selectItems value="#{paramRdvController.allChoix}"/>
		</h:selectOneRadio>
		
		
		<e:outputLabel value="#{msgs['FIELD_LABEL.LIST_CGE']}"
			styleClass="formHeader" for="listCge"
			rendered="#{paramRdvController.choixCge}"/>
		<t:selectOneMenu id="listCge"
			styleClass="oneMenu"
			rendered="#{paramRdvController.choixCge}"
			value="#{paramRdvController.codeCge}">
			<f:selectItems value="#{paramRdvController.allCge}"/>
		</t:selectOneMenu>
		
		
		<e:outputLabel value="#{msgs['FIELD_LABEL.LIST_COMMISSION']}"
			styleClass="formHeader" for="listCommission"
			rendered="#{paramRdvController.choixCommission}"/>
		<t:panelGrid columns="3" id="listCommission" 
			rendered="#{paramRdvController.choixCommission}">
			<t:selectManyMenu styleClass="manyMenu"
				value="#{paramRdvController.selectCommADI}">
				<f:selectItems value="#{paramRdvController.allCommItems}"/>
			</t:selectManyMenu>
			<t:panelGrid columns="1">
				<e:commandButton image="/media/images/fleche_gauche.gif"
					styleClass="icone"
					action="#{paramRdvController.suppCommItems}"/>
				<e:commandButton image="/media/images/fleche_droite.gif"
					styleClass="icone"
					action="#{paramRdvController.ajouCommItems}"/>
			</t:panelGrid>
			<t:selectManyMenu styleClass="manyMenu"
				value="#{paramRdvController.selectCommDI}">
				<f:selectItems value="#{paramRdvController.commItems}"/>
			</t:selectManyMenu>
		</t:panelGrid>
		
		<e:outputLabel value="#{msgs['FIELD_LABEL.LIST_VET']}"
			styleClass="formHeader" for="listVet"
			rendered="#{paramRdvController.choixVet}"/>
		<t:panelGrid columns="3" id="listVet" 
			rendered="#{paramRdvController.choixVet}">
			<t:selectManyMenu styleClass="manyMenu"
				value="#{paramRdvController.selectVetADI}">
				<f:selectItems value="#{paramRdvController.allVetItems}"/>
			</t:selectManyMenu>
			<t:panelGrid columns="1">
				<e:commandButton image="/media/images/fleche_gauche.gif"
					styleClass="icone"
					action="#{paramRdvController.suppVetItems}"/>
				<e:commandButton image="/media/images/fleche_droite.gif"
					styleClass="icone"
					action="#{paramRdvController.ajouVetItems}"/>
			</t:panelGrid>
			<t:selectManyMenu styleClass="manyMenu"
				value="#{paramRdvController.selectVetDI}">
				<f:selectItems value="#{paramRdvController.vetItems}"/>
			</t:selectManyMenu>
		</t:panelGrid>
		
		
		<e:outputLabel value="#{msgs['FIELD_LABEL.MSG_ETUDIANT']}"
			styleClass="formHeader" for="msgEtudiant"/>
		<fck:editor id="msgEtudiant" 
			value="#{paramRdvController.calendarRDV.msgEtudiant}"
			rows="4"
			toolbarSet="Comment" >				
			<f:validateLength minimum="0" maximum="4000" />
		</fck:editor>
		
		
		<e:outputLabel value="#{msgs['FIELD_LABEL.PARTICIPE']}"
			styleClass="formHeader" for="participe"/>
		<e:selectBooleanCheckbox id="participe"
			immediate="true"	
			onchange="submit();"
			value="#{paramRdvController.calendarRDV.participeOK}"/>
		
		
		<e:outputLabel value="#{msgs['FIELD_LABEL.DATE_DEBUT']}"
			styleClass="formHeader" for="dateDebut"
			rendered="#{paramRdvController.calendarRDV.participeOK}"/>
		<t:inputCalendar id="dateDebut" size="10" immediate="true"
			rendered="#{paramRdvController.calendarRDV.participeOK}"	
			value="#{paramRdvController.calendarRDV.dateDebutInsc}" 
			renderAsPopup="true"
			renderPopupButtonAsImage="true" 
			popupDateFormat="dd/MM/yy"
			popupTodayString="#{msgs['CALENDAR.TODAY']}"
			popupWeekString="#{msgs['CALENDAR.WK']}">
			<f:convertDateTime timeZone="Europe/Paris" locale="fr_FR" type="date" dateStyle="short" pattern="dd/MM/yy" />
		</t:inputCalendar>
		
		
		<e:outputLabel value="#{msgs['FIELD_LABEL.DATE_FIN']}"
			styleClass="formHeader" for="dateFin"
			rendered="#{paramRdvController.calendarRDV.participeOK}"/>
		<t:inputCalendar id="dateFin" size="10" immediate="true"
			rendered="#{paramRdvController.calendarRDV.participeOK}"	
			value="#{paramRdvController.calendarRDV.dateFinInsc}"					
			renderAsPopup="true"
			renderPopupButtonAsImage="true" 
			popupDateFormat="dd/MM/yy"
			popupTodayString="#{msgs['CALENDAR.TODAY']}"
			popupWeekString="#{msgs['CALENDAR.WK']}">
			<f:convertDateTime timeZone="Europe/Paris" locale="fr_FR" type="date" dateStyle="short" pattern="dd/MM/yy" />
		</t:inputCalendar>
		
		
		<e:outputLabel value="#{msgs['FIELD_LABEL.NB_ETUDIANT_PAR_HEURE']}"
			styleClass="formHeader" for="nbEtudiantParHeure"
			rendered="#{paramRdvController.calendarRDV.participeOK}"/>
		<e:inputText id="nbEtudiantParHeure" maxlength="2" size="4"
			rendered="#{paramRdvController.calendarRDV.participeOK}"
			value="#{paramRdvController.calendarRDV.nbreMaxEtud}">
			<t:validateRegExpr pattern="^([1-9]{1})([0-9]{0,1})$" />
		</e:inputText>
			
			
		<e:outputLabel value="#{msgs['FIELD_LABEL.NB_JOURS_OUVRES']}"
			styleClass="formHeader" for="nbJoursOuvres"
			rendered="#{paramRdvController.calendarRDV.participeOK}"/>
		<e:inputText id="nbJoursOuvres" size="2"
			rendered="#{paramRdvController.calendarRDV.participeOK}"
			value="#{paramRdvController.calendarRDV.nbJoursAvantPro}" >
			<t:validateRegExpr pattern="^([0-9]{1})([0-9]*)$" />
		</e:inputText>
			
			
		<e:outputLabel value="#{msgs['FIELD_LABEL.NB_JOURS_PROPOSES']}"
			styleClass="formHeader" for="nbJoursProposes"
			rendered="#{paramRdvController.calendarRDV.participeOK}"/>
		<e:inputText id="nbJoursProposes" size="3"
			rendered="#{paramRdvController.calendarRDV.participeOK}"
			value="#{paramRdvController.calendarRDV.nbJoursApresPro}" >
			<t:validateRegExpr pattern="^([1-9]{1})([0-9]*)$"  />
		</e:inputText>
		
		
		<e:outputLabel value="#{msgs['FIELD_LABEL.MSG_MAIL']}"
			styleClass="formHeader" for="msgMail"
			rendered="#{paramRdvController.calendarRDV.participeOK}"/>
		<fck:editor id="msgMail" rows="4" toolbarSet="Empty"
			rendered="#{paramRdvController.calendarRDV.participeOK}"
			value="#{paramRdvController.calendarRDV.msgMailConfirmation}">
			<f:validateLength minimum="0" maximum="4000"/>
		</fck:editor>
		
		
		<e:outputLabel value="#{msgs['FIELD_LABEL.MSG_VALIDATION']}"
			styleClass="formHeader" for="msgValidation"
			rendered="#{paramRdvController.calendarRDV.participeOK}"/>
		<fck:editor id="msgValidation" rows="4" toolbarSet="Comment"
			rendered="#{paramRdvController.calendarRDV.participeOK}"
			value="#{paramRdvController.calendarRDV.msgValidation}">
			<f:validateLength minimum="0" maximum="2000"/>
		</fck:editor>
	</t:panelGrid>
	
	
	<e:message for="seeRdvForm"/>

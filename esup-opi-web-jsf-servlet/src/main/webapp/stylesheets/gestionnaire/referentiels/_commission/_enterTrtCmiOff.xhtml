<%@include file="../../../_include/_include.jsp"%>
<t:div rendered="#{not empty trtCmiController.treatmentsCmiOff}">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['FIELD_LABEL.ETAPES_OFF']}"
				styleClass="section-smallTitle" />
			<t:panelGroup>
				<e:commandButton value="Ajouter une campagne"
					rendered="#{trtCmiController.actionEnum.whatAction 
						== trtCmiController.actionEnum.readAction}">
					<t:updateActionListener
						value="#{trtCmiController.actionEnum.addAction}"
						property="#{trtCmiController.actionEnum.whatAction}" />
				</e:commandButton>
				<e:commandButton value="#{msgs['_.BUTTON.ETAPES.ADD_CAMPAGNE']}" 
					rendered="#{trtCmiController.actionEnum.whatAction 
						== trtCmiController.actionEnum.addAction}"
					action="#{trtCmiController.addCampToVet}" />
				<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
					rendered="#{trtCmiController.actionEnum.whatAction 
						== trtCmiController.actionEnum.addAction}">
					<t:updateActionListener
						value="#{trtCmiController.actionEnum.readAction}"
						property="#{trtCmiController.actionEnum.whatAction}" />
				</e:commandButton>
			</t:panelGroup>
				
		</e:panelGrid>
	</t:div>
	<t:htmlTag value="br" rendered="#{trtCmiController.actionEnum.whatAction 
			== trtCmiController.actionEnum.addAction}"/>				
	<e:panelGrid styleClass="tableWidthMax" columns="2"
		columnClasses="col1UnDemi,col2UnDemi" 
		rendered="#{trtCmiController.actionEnum.whatAction 
			== trtCmiController.actionEnum.addAction}">
		<t:panelGroup>
			<e:outputLabel value="#{msgs['CAMPAGNES.SELECT']}" for="campagne" />
		</t:panelGroup>
		<t:panelGroup>
			<e:selectOneMenu id="campagne" converter="#{campagneConverter}"
				value="#{trtCmiController.campagneSelected}">
				<t:selectItems var="camp"
					value="#{nomenclatureController.campagnesItems}"
					itemValue="#{camp}"
					itemLabel="#{camp.code} : #{camp.libelle}" />
			</e:selectOneMenu>
		</t:panelGroup>
	</e:panelGrid>
	<t:htmlTag value="br" rendered="#{trtCmiController.actionEnum.whatAction 
			== trtCmiController.actionEnum.addAction}"/>				
	
	<e:dataTable id="trtCmiOff" var="beanTrtCmi"
		value="#{trtCmiController.treatmentsCmiOff}" rowIndexVar="rowVar"
		styleClass="displayInfo" alternateColors="false">
		<t:column width="10%">
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.CGE']}" />
			</f:facet>
			<e:text value="#{beanTrtCmi.traitementCmi.versionEtpOpi.codCge}" />
		</t:column>
		<t:column width="35%">
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.ETP']}" />
			</f:facet>
			<e:text value="#{beanTrtCmi.etape.libWebVet}" />
		</t:column>
		<t:column width="10%">
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.CAMPAGNES']}" />
			</f:facet>
			<t:popup styleClass="containerPopup" style="width:300px;"
				closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
				displayAtDistanceX="01" displayAtDistanceY="0">
				<t:graphicImage url="/media/images/informationSmall.png"
					style="cursor:pointer;" id="allcampagnes" />

				<f:facet name="popup">
					<h:panelGroup>
						<h:panelGrid columns="1">
							<t:outputText styleClass="titrePopupAide"
								value="#{msgs['FIELD_LABEL.CAMPAGNES']}" />
							<t:panelGroup>
								<t:outputText styleClass="libellesAide"
									value="#{msgs['CAMPAGNES.INFO.VET']} : " />
							</t:panelGroup>
							<t:panelGroup>
								<t:dataList var="link"
									value="#{beanTrtCmi.traitementCmi.linkTrtCmiCamp}"
									layout="unorderedList">
									<t:column>
										<e:text value="#{link.campagne.code}"
											styleClass="span-text-small" />
										<e:text value=" : " styleClass="span-text-small" />
										<e:text value="#{link.campagne.libelle}"
											styleClass="span-text-small" />
									</t:column>
								</t:dataList>
							</t:panelGroup>
						</h:panelGrid>
					</h:panelGroup>
				</f:facet>
			</t:popup>

		</t:column>
		<t:column width="15%">
			<f:facet name="header">
				<t:outputText value="#{msgs['TYP_TRT']}" />
			</f:facet>
			<e:text value="#{beanTrtCmi.typeTraitement.label}" />
		</t:column>
		<t:column width="3%"
			rendered="#{trtCmiController.actionEnum.whatAction 
							== trtCmiController.actionEnum.addAction}">
			<f:facet name="header">
				<t:selectBooleanCheckbox
					value="#{etapeController.allChecked}"
					onclick="checkAllInElement('campToVetForm:trtCmiOff' ,this.checked)" />
			</f:facet>
			<jdt:multipleRowsSelector
				selectionList="#{etapeController.objectToAdd}" />
		</t:column>
	</e:dataTable>

</t:div>

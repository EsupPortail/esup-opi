<%@include file="../../../_include/_include.jsp"%>

<t:div id="blockFormAdresseFix" styleClass="blockForm">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['ADRESS']}" styleClass="section-smallTitle" />
			<t:panelGroup />
		</e:panelGrid>
	</t:div>
	<e:panelGrid styleClass="tableWidthMax" columns="4"
		columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
		<t:panelGroup>
			<e:outputLabel value="#{msgs['ADRESS.PAY']}" for="paysAdr" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<e:selectOneMenu id="paysAdr"
			value="#{adressController.fixAdrPojo.adresse.codPays}">
			<f:selectItem itemLabel="" itemValue="" />
			<t:selectItems var="pays" value="#{adressController.pays}"
				itemLabel="#{pays.libPay}" itemValue="#{pays.codPay}" />
		</e:selectOneMenu>
		<t:panelGroup />
		<t:panelGroup />

		<t:panelGroup>
			<e:outputLabel value="#{msgs['ADRESS']}" for="adr1" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<e:inputText id="adr1"
			value="#{adressController.fixAdrPojo.adresse.adr1}" size="32"
			maxlength="30" />
		<e:outputLabel value="#{msgs['ADRESS.COMP']}" for="adr2" />
		<e:inputText id="adr2"
			value="#{adressController.fixAdrPojo.adresse.adr2}" size="40"
			maxlength="70" />
		<e:outputLabel value="#{msgs['ADRESS.COMP']}" for="adr3" />
		<e:inputText id="adr3"
			value="#{adressController.fixAdrPojo.adresse.adr3}" size="40"
			maxlength="70" />
		<t:panelGroup>
			<e:outputLabel value="#{msgs['ADRESS.COD_POST']}" for="codePostalFix" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup>
			<e:inputText id="codePostalFix" value="#{adressController.fixAdrPojo.adresse.codBdi}" 
				size="6" maxlength="5" />
			<e:commandButton id="submitSelectCpFix" action="#{adressController.selectCpFix}"/>
		</t:panelGroup>

		<t:panelGroup>
			<e:outputLabel value="#{msgs['ADRESS.VIL']}" for="ville" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<e:selectOneMenu id="ville"
			value="#{adressController.fixAdrPojo.adresse.codCommune}">
			<t:selectItems var="ville" value="#{adressController.communesFix}"
				itemLabel="#{ville.libCommune}" itemValue="#{ville.codeCommune}" />
		</e:selectOneMenu>



		<t:panelGroup>
			<t:selectBooleanCheckbox
				value="#{adressController.fixAdrPojo.isCedex}" />
			<e:outputLabel id="labeladrCedex" value=" #{msgs['ADRESS.CEDEX']}"
				for="cedex" />
		</t:panelGroup>
		<t:panelGrid columns="2"
			columnClasses="td1_ContentMess,td2_ContentMess" style="width: 100%;">
			<e:inputText id="cedex" size="4" maxlength="4"
				value="#{adressController.fixAdrPojo.adresse.cedex}" />
			<t:popup styleClass="containerPopup" style="width:200px;"
				closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
				displayAtDistanceX="-200" displayAtDistanceY="0">
				<t:graphicImage url="/media/images/help.png"
					title="#{msgs['ADRESS.INFO.POST.CEDEX'] }" style="cursor:pointer;" />

				<f:facet name="popup">
					<h:panelGroup>
						<h:panelGrid columns="1">
							<t:outputText styleClass="titrePopupAide"
								value="#{msgs['ADRESS.CEDEX']}" />
							<t:panelGroup>
								<t:outputText styleClass="libellesAide"
									value="#{msgs['ADRESS.INFO.POST.CEDEX'] }" />
							</t:panelGroup>
						</h:panelGrid>
					</h:panelGroup>
				</f:facet>
			</t:popup>

		</t:panelGrid>
	</e:panelGrid>
</t:div>
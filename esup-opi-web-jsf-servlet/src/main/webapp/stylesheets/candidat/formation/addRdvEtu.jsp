<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}">
	
	<%@include file="../../_include/_header.jsp"%>
	
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGlobal.jsp"%>
	</h:form>
	
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['CALENDAR_RDV.ADD.TITLE']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<e:form id="saisieRdvForm" styleClass="opiR1_form">
				<t:div id="div_subSection" styleClass="div_subSection">
					<t:div id="blockCandidaturesConfirm" styleClass="blockForm">
						<t:div style="width:100%;">
							<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
								styleClass="tableJustWidthMax">
								<e:text value="#{msgs['CALENDAR_RDV.TITRE_CHOICE']}"
									styleClass="section-smallTitle" />
								<e:commandButton value="#{msgs['_.BUTTON.BACK.WELCOME']}" immediate="true"
									rendered="#{saisieRdvEtuController.actionEnum.whatAction == saisieRdvEtuController.actionEnum.emptyAction}"
									action="#{accueilController.goWelcomeCandidat}" />
							</e:panelGrid>
						</t:div>
						<t:panelGroup id="rendezVous"
							rendered="#{saisieRdvEtuController.actionEnum.whatAction == saisieRdvEtuController.actionEnum.emptyAction}">
								<t:htmlTag value="br" />
								<t:div styleClass="blockImportant" style="text-align: center">
									<e:text value="#{msgs['CALENDAR_RDV.TITRE_RDV']}" escape="false">
										<f:param value="#{saisieRdvEtuController.voeu.vrsEtape.libWebVet}"/>
									</e:text>
									<t:htmlTag value="br" />
									<t:htmlTag value="br" />
									<e:text value="#{msgs['CALENDAR_RDV.VOEU_NO_DATE']}"
										rendered="#{saisieRdvEtuController.dateSelected == null && saisieRdvEtuController.autorizedDate}"/>
									
									<e:text value="#{msgs['CALENDAR_RDV.VOEU_DATE']}" escape="false"
										rendered="#{saisieRdvEtuController.dateSelected != null && saisieRdvEtuController.autorizedDate}">
										<f:param value="#{saisieRdvEtuController.dateSelected}" />
										<f:convertDateTime pattern="dd/MM/yyyy HH:mm"
												timeZone="#{sessionController.timezone}" />
									</e:text>
									
									<e:text value="#{msgs['CALENDAR_RDV.VOEU_DATE_NO_AUTORIZED']}" escape="false"
										rendered="#{!saisieRdvEtuController.autorizedDate}">
										<f:param value="#{saisieRdvEtuController.dateSelected}" />
										<f:convertDateTime pattern="dd/MM/yyyy HH:mm"
												timeZone="#{sessionController.timezone}" />
									</e:text>
									<t:htmlTag value="br" />
									<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
										rendered="#{saisieRdvEtuController.dateSelected != null 
												&& sessionController.currentInd.asRightsToUpdate}"
										immediate="true">
										<t:updateActionListener value="#{saisieRdvEtuController.actionEnum.addAction}" 
											property="#{saisieRdvEtuController.actionEnum.whatAction}"/>
									</e:commandButton>
								</t:div>		
								
								<t:panelGroup id="choixRdv"
									rendered="#{saisieRdvEtuController.autorizedDate
										&& saisieRdvEtuController.weekScheduleModel != null}">
									<e:panelGrid columns="3" cellpadding="0" cellspacing="1" align="middle" 
										columnClasses="colRDVLeft,colRDVCenter,colRDVRight">
										<t:commandLink id="clmoisprec" immediate="true"
											actionListener="#{saisieRdvEtuController.before}">
											<t:graphicImage border="0" value="/media/images/backward.png"
												alt="#{msgs['CALENDAR_RDV.BUTTON.PREC_MONTH']}" />
										</t:commandLink>
										<t:div style="text-align:center; width:850px;">
											<e:subSection style="text-transform: uppercase;"
												value="#{saisieRdvEtuController.weekScheduleModel.selectedDate}">
												<f:convertDateTime type="date" pattern="MMMM yyyy"
													timeZone="Europe/Paris" />
											</e:subSection>
									
											<t:schedule id="weekCalendar"
												value="#{saisieRdvEtuController.weekScheduleModel}"
												theme="evolution"
												headerDateFormat="EEEE d MMMM yyyy" tooltip="true"
												submitOnClick="true" selectedEntryClass="schedule_selected"
												entryRenderer="#{extendedEntryRenderer}"
												visibleStartHour="#{saisieRdvEtuController.startHour}"
												visibleEndHour="#{saisieRdvEtuController.endHour}"
												action="#{saisieRdvEtuController.scheduleAction}"/>
										</t:div>
									
										<t:commandLink id="clmoissuiv" immediate="true"
											actionListener="#{saisieRdvEtuController.next}">
											<t:graphicImage border="0" value="/media/images/forward.png"
												alt="#{msgs['CALENDAR_RDV.BUTTON.NEXT_MONTH']}" />
										</t:commandLink>
									</e:panelGrid>
								</t:panelGroup>
							
						</t:panelGroup>
						<t:panelGroup id="confirmerLaValidation" 
							rendered="#{saisieRdvEtuController.actionEnum.whatAction == saisieRdvEtuController.actionEnum.addAction}">
							<t:htmlTag value="br" />
							<t:div styleClass="blockImportant" style="text-align: center">
								<e:text value="#{msgs['CALENDAR_RDV.VALIDER_RDV']}" escape="false">
									<f:param value="#{saisieRdvEtuController.voeu.vrsEtape.libWebVet}"/>
									<f:param value="#{saisieRdvEtuController.dateSelected}" />
									<f:convertDateTime pattern="dd/MM/yyyy HH:mm"
												timeZone="#{sessionController.timezone}" />
								</e:text>
								
								<e:commandButton value="#{msgs['_.BUTTON.YES']}"
									action="#{saisieRdvEtuController.validerRdv}"
									immediate="true"/>
								
								<e:commandButton value="#{msgs['_.BUTTON.NO']}" 
									immediate="true">
									<t:updateActionListener value="#{saisieRdvEtuController.actionEnum.emptyAction}" 
										property="#{saisieRdvEtuController.actionEnum.whatAction}"/>
								</e:commandButton>
							</t:div>
						</t:panelGroup>
					</t:div>
				</t:div>
			</e:form>
		</t:div>
	</t:div>
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navGlobal.jsp"%>
		</h:form>
	</t:div>
	
	<script type="text/javascript">
		highlightChildrenLiTags('navigationHeader:navigationLogin');
		highlightChildrenLiTags('navigationFooter:navigationLogin');
	</script>
</e:page>

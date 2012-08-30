<%@include file="../../../_include/_include.jsp"%>
<h:form id="formDeleteCmi">
	<t:div style="width:100%;">
		<e:dataTable id="versionEtapeDTO" var="vetDTO"
			value="#{trtCmiController.listVETToTransfert}" rowIndexVar="rowVar"
			styleClass="displayInfo" alternateColors="false">
			<t:column>
				<e:text value="#{msgs['ETAPES.CONFIRM.TRANSFERT']}">
					<f:param value="#{vetDTO.codEtp}"/>
					<f:param value="#{vetDTO.codVrsVet}"/>
					<f:param value="#{vetDTO.licEtp}"/>
				</e:text>
			</t:column>
			<t:column width="10%">
				<t:panelGroup>	
					<e:commandButton value="#{msgs['_.BUTTON.YES']}"
						action="#{trtCmiController.transfertVetToComm}">
						<t:updateActionListener value="#{true}"
							property="#{trtCmiController.transfertVet}" />
						<t:updateActionListener value="#{vetDTO}"
							property="#{trtCmiController.vetToTransfert}" />
					</e:commandButton>
					<e:commandButton value="#{msgs['_.BUTTON.NO']}"
						action="#{trtCmiController.transfertVetToComm}">
						<t:updateActionListener value="#{false}"
							property="#{trtCmiController.transfertVet}" />
						<t:updateActionListener value="#{vetDTO}"
							property="#{trtCmiController.vetToTransfert}" />
					</e:commandButton>
				</t:panelGroup>
			</t:column>

		</e:dataTable>
	</t:div>
</h:form>

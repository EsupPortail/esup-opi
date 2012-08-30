<%@include file="../../../_include/_include.jsp"%>
<t:div style="width:100%;">
	<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
		styleClass="tableJustWidthMax">
		<e:text value="#{msgs['COORDONNE.CONFIRM.DELETE']}">
			<f:param value="#{parametreStatController.selectOrdonneCoordonne}"/>
		</e:text>
		<t:panelGroup>
			<e:commandButton value="#{msgs['_.BUTTON.YES']}"
				action="#{parametreStatController.delOrdonneCoordonne}">
				<t:updateActionListener value="#{parametreStatController.actionEnum.emptyAction}"
					property="#{parametreStatController.actionEnum.whatAction}"/>
				<t:updateActionListener value="#{parametreStatController.addUpdateCoordonne.emptyAction}"
					property="#{parametreStatController.addUpdateCoordonne.whatAction}"/>
			</e:commandButton>
			<e:commandButton value="#{msgs['_.BUTTON.NO']}">
				<t:updateActionListener value="#{parametreStatController.actionEnum.emptyAction}"
					property="#{parametreStatController.actionEnum.whatAction}"/>
				<t:updateActionListener value="#{parametreStatController.addUpdateCoordonne.emptyAction}"
					property="#{parametreStatController.addUpdateCoordonne.whatAction}"/>
			</e:commandButton>
		</t:panelGroup>
	</e:panelGrid>
</t:div>

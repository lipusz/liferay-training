<%@ include file="./init.jsp" %>

<p>
	<b><liferay-ui:message key="msg.caption" /></b>
</p>

<liferay-ui:success key="entryAdded" message="msg.entry-added" />
<liferay-ui:success key="entryUpdated" message="msg.entry-updated" />
<liferay-ui:success key="entryDeleted" message="msg.entry-deleted" />

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="mvcPath" value="/web/search.jsp" />
</liferay-portlet:renderURL>

<portlet:renderURL var="viewURL">
	<portlet:param name="mvcPath" value="/web/view.jsp" />
</portlet:renderURL>

<%@ include file="/META-INF/resources/search/form.jsp" %>

<clay:navigation-bar navigationItems="${navigationItems}" />

<gb:if-guestbook-permission
	actionId="VIEW"
	guestbookId="${guestbookId}"
	permissionChecker="${permissionChecker}"
>
	<liferay-ui:search-container total="${total}">
		<liferay-ui:search-container-results results="${results}" />

		<liferay-ui:search-container-row className="com.liferay.blade.samples.guestbook.model.Entry" modelVar="entry">
			<gb:if-entry-permission
				actionId="VIEW"
				entryId="${entry.entryId}"
				permissionChecker="${permissionChecker}"
			>
				<liferay-ui:search-container-column-text property="message" />
				<liferay-ui:search-container-column-text property="name" />
				<liferay-ui:search-container-column-text property="email" />

				<liferay-ui:search-container-column-status property="status" />

				<liferay-ui:search-container-column-jsp align="right" path="/web/actions.jsp" />
			</gb:if-entry-permission>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</gb:if-guestbook-permission>

<gb:if-guestbook-permission
	actionId="ADD_ENTRY"
	guestbookId="${guestbookId}"
	permissionChecker="${permissionChecker}"
>
	<portlet:renderURL var="addEntryURL">
		<portlet:param name="mvcPath" value="/web/edit.jsp" />
		<portlet:param name="guestbookId" value="${guestbookId}" />
	</portlet:renderURL>

	<aui:button-row>
		<aui:button onClick="${addEntryURL}" value="Add Entry" />
	</aui:button-row>
</gb:if-guestbook-permission>
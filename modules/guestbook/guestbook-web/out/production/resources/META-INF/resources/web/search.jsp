<%@include file="./init.jsp" %>

<liferay-portlet:renderURL varImpl="searchURL">
    <portlet:param name="mvcPath" value="/web/search.jsp"/>
</liferay-portlet:renderURL>

<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/web/view.jsp"/>
</portlet:renderURL>

<aui:form action="${searchURL}" method="get" name="fm">

    <liferay-portlet:renderURLParams varImpl="searchURL"/>
    <liferay-ui:header backURL="${viewURL}" title="search"/>

    <div class="search-form">
        <span class="aui-search-bar">
            <aui:input inlineField="${true}" name="keywords" size="30" title="search-entries"/>
            <aui:button type="submit" value="search"/>
        </span>
    </div>
</aui:form>

<liferay-ui:search-container delta="10" emptyResultsMessage="no-entries-were-found" total="${entries['size']}">
    <liferay-ui:search-container-results results="${entries}"/>

    <liferay-ui:search-container-row
            className="br.com.objective.training.model.Entry"
            keyProperty="entryId"
            modelVar="entry"
            escapedModel="${true}"
    >
        <liferay-ui:search-container-column-text name="guestbook" value="${guestbookMap[entry.guestbookId]}"/>
        <liferay-ui:search-container-column-text property="message"/>
        <liferay-ui:search-container-column-text property="name"/>
        <liferay-ui:search-container-column-jsp path="/web/actions.jsp" align="right"/>
    </liferay-ui:search-container-row>
    <liferay-ui:search-iterator/>
</liferay-ui:search-container>
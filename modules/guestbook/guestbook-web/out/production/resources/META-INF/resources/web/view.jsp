<%@ taglib prefix="bg" uri="http://training.objective.com.br/tld/guestbook" %>
<%@ include file="./init.jsp" %>

<p>
    <b><liferay-ui:message key="msg.caption"/></b>
</p>

<liferay-ui:success key="entryAdded" message="msg.entry-added"/>
<liferay-ui:success key="entryUpdated" message="msg.entry-updated"/>
<liferay-ui:success key="entryDeleted" message="msg.entry-deleted"/>

<aui:nav>
    <c:forEach items="${guestbooks}" var="guestbook">
        <gb:if-guestbook-permission
                permissionChecker="${permissionChecker}" guestbookId="${guestbookId}" actionId="VIEW">

            <portlet:renderURL var="viewPageURL">
                <portlet:param name="mvcPath" value="/web/view.jsp"/>
                <portlet:param name="guestbookId" value="${guestbook.guestbookId}"/>
            </portlet:renderURL>

            <c:choose>
                <c:when test="${guestbook.guestbookId eq guestbookId}">
                    <c:set var="cssClass" value="active"/>
                </c:when>
                <c:otherwise>
                    <c:set var="cssClass" value=""/>
                </c:otherwise>
            </c:choose>

            <aui:nav-item cssClass="${cssClass}" href="${viewPageURL}" label="${guestbook.name}"/>
        </gb:if-guestbook-permission>
    </c:forEach>
</aui:nav>

<liferay-portlet:renderURL varImpl="searchURL">
    <portlet:param name="mvcPath" value="/web/search.jsp"/>
</liferay-portlet:renderURL>

<aui:form action="${searchURL}" method="get" name="fm">
    <liferay-portlet:renderURLParams varImpl="searchURL"/>
    <div class="search-form">
        <span class="aui-search-bar">
            <aui:input
                    inlineField="${true}"
                    label=""
                    name="keywords"
                    size="30"
                    title="search-entries"
                    type="text"
            />
            <aui:button type="submit" value="search"/>
        </span>
    </div>
</aui:form>

<gb:if-guestbook-permission
        permissionChecker="${permissionChecker}" guestbookId="${guestbookId}" actionId="VIEW">
    <liferay-ui:search-container total="${total}">
        <liferay-ui:search-container-results results="${results}"/>

        <liferay-ui:search-container-row className="br.com.objective.training.model.Entry" modelVar="entry">

            <gb:if-entry-permission
                    permissionChecker="${permissionChecker}" entryId="${entry.entryId}" actionId="VIEW">
                <liferay-ui:search-container-column-text property="message"/>
                <liferay-ui:search-container-column-text property="name"/>
                <liferay-ui:search-container-column-text property="email"/>
                <liferay-ui:search-container-column-jsp align="right" path="/web/actions.jsp"/>
            </gb:if-entry-permission>
        </liferay-ui:search-container-row>

        <liferay-ui:search-iterator/>
    </liferay-ui:search-container>
</gb:if-guestbook-permission>

<gb:if-guestbook-permission
        permissionChecker="${permissionChecker}" guestbookId="${guestbookId}" actionId="ADD_ENTRY">
    <portlet:renderURL var="addEntryURL">
        <portlet:param name="mvcPath" value="/web/edit.jsp"/>
        <portlet:param name="guestbookId" value="${guestbookId}"/>
    </portlet:renderURL>

    <aui:button-row>
        <aui:button onClick="${addEntryURL}" value="Add Entry"/>
    </aui:button-row>
</gb:if-guestbook-permission>
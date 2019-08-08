<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<%--<div class="jumbotron pt-4">
    <div class="container">
        <section>
            <h3><spring:message code="meal.title"/></h3>

            <form method="get" action="meals/filter">
                <dl>
                    <dt><spring:message code="meal.startDate"/>:</dt>
                    <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code="meal.endDate"/>:</dt>
                    <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code="meal.startTime"/>:</dt>
                    <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code="meal.endTime"/>:</dt>
                    <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
                </dl>
                &lt;%&ndash;<button type="submit"><spring:message code="meal.filter"/></button>&ndash;%&gt;
                <button class="btn btn-primary" onclick="updateFilteredTable()">
                    <span class="fa fa-filter"></span>
                    Отфильтровать
                </button>

            </form>
            <hr>
            &lt;%&ndash;<a href="meals/create"><spring:message code="meal.add"/></a>&ndash;%&gt;
            <button class="btn btn-primary" onclick="add()">
                <span class="fa fa-plus"></span>
                Добавить
            </button>
            <hr>
            <table class="table table-striped" id="datatable">
                <thead>
                <tr>
                    <th><spring:message code="meal.dateTime"/></th>
                    <th><spring:message code="meal.description"/></th>
                    <th><spring:message code="meal.calories"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <c:forEach items="${meals}" var="meal">
                    <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>
                    <tr data-mealExcess="${meal.excess}">
                        <td>
                                &lt;%&ndash;${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}&ndash;%&gt;
                                &lt;%&ndash;<%=TimeUtil.toString(meal.getDateTime())%>&ndash;%&gt;
                                &lt;%&ndash;${fn:replace(meal.dateTime, 'T', ' ')}&ndash;%&gt;
                                ${fn:formatDateTime(meal.dateTime)}
                        </td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>
                        <td><a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a></td>
                    </tr>
                </c:forEach>
            </table>
        </section>
    </div>
</div>--%>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center">Моя еда</h3>

        <div class="card border-dark">
            <div class="card-body pb-0">
                <form id="filter">
                    <div class="row">
                        <div class="offset-1 col-2">
                            <label for="startDate">От даты</label>
                            <input class="form-control" name="startDate" id="startDate">
                        </div>
                        <div class="col-2">
                            <label for="endDate">До даты</label>
                            <input class="form-control" name="endDate" id="endDate">
                        </div>
                        <div class="offset-2 col-2">
                            <label for="startTime">От времени</label>
                            <input class="form-control" name="startTime" id="startTime">
                        </div>
                        <div class="col-2">
                            <label for="endTime">До времени</label>
                            <input class="form-control" name="endTime" id="endTime">
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-footer text-right">
                <button class="btn btn-danger" onclick="clearFilter()">
                    <span class="fa fa-remove"></span>
                    Отменить
                </button>
                <button class="btn btn-primary" onclick="updateFilteredTable()">
                    <span class="fa fa-filter"></span>
                    Отфильтровать
                </button>
            </div>
        </div>
        <br>
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            Добавить
        </button>

        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="meal.dateTime"/></th>
                <th><spring:message code="meal.description"/></th>
                <th><spring:message code="meal.calories"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>
                <tr data-mealExcess="${meal.excess}">
                    <td>${fn:formatDateTime(meal.dateTime)}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>
                    <td><a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
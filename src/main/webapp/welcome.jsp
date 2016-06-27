<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>query</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/static/bootstrap/3.3.5/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/bootstrap/3.3.5/css_cerulean/font-awesome.min.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/bootstrap/3.3.5/css_cerulean/ace.min.css" type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/static/bootstrap/3.3.5/js/ace-extra.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap/3.3.5/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap/3.3.5/js/ace.min.js" type="text/javascript"></script>
<!--[if lte IE 7]><link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome-ie7.min.css" type="text/css" rel="stylesheet" /><![endif]-->
<!--[if lte IE 6]><link href="${ctxStatic}/bootstrap/bsie/css/bootstrap-ie6.min.css" type="text/css" rel="stylesheet" />-->

<style type="text/css">
table, th, td
  {
  border: 1px solid blue;
  }
  
table .header-fixed {
    position: fixed;
    top: 0px;
    /* 10 less than .navbar-fixed to prevent any overlap */
    
    z-index: 1020;
    border-bottom: 1px solid #d5d5d5;
    -webkit-border-radius: 0;
    -moz-border-radius: 0;
    border-radius: 0;
    -webkit-box-shadow: inset 0 1px 0 #fff, 0 1px 5px rgba(0, 0, 0, 0.1);
    -moz-box-shadow: inset 0 1px 0 #fff, 0 1px 5px rgba(0, 0, 0, 0.1);
    box-shadow: inset 0 1px 0 #fff, 0 1px 5px rgba(0, 0, 0, 0.1);
    /* IE6-9 */
    
    filter: progid: DXImageTransform.Microsoft.gradient(enabled=false);
}

</style>
<script type="text/javascript">
function commitQuery(para){
	document.getElementById("myForm").action="<%=request.getContextPath() %>/query/"  + para;
	document.getElementById("myForm").submit();
}

(function() {
    (function($) {
        return $.fn.fixedHeader = function(options) {
            var config;
            config = {
                topOffset: 40,
                bgColor: "#EEEEEE"
            };
            if (options) {
                $.extend(config, options);
            }
            return this.each(function() {
                var $head, $win, headTop, isFixed, o, processScroll, ww;
                processScroll = function() {
                    var headTop, i, isFixed, scrollTop, t;
                    if (!o.is(":visible")) {
                        return;
                    }
                    i = void 0;
                    scrollTop = $win.scrollTop();
                    t = $head.length && $head.offset().top - config.topOffset;
                    if (!isFixed && headTop !== t) {
                        headTop = t;
                    }
                    if (scrollTop >= headTop && !isFixed) {
                        isFixed = 1;
                    } else {
                        if (scrollTop <= headTop && isFixed) {
                            isFixed = 0;
                        }
                    }
                    if (isFixed) {
                        return $("thead.header-copy", o).removeClass("hide");
                    } else {
                        return $("thead.header-copy", o).addClass("hide");
                    }
                };
                o = $(this);
                $win = $(window);
                $head = $("thead.header", o);
                isFixed = 0;
                headTop = $head.length && $head.offset().top - config.topOffset;
                $win.on("scroll", processScroll);
                $head.on("click", function() {
                    if (!isFixed) {
                        return setTimeout((function() {
                            return $win.scrollTop($win.scrollTop() - 47);
                        }), 10);
                    }
                });
                $head.clone().removeClass("header").addClass("header-copy header-fixed").appendTo(o);
                ww = [];
                o.find("thead.header > tr:first > th").each(function(i, h) {
                    return ww.push($(h).width());
                });
                $.each(ww, function(i, w) {
                    return o.find("thead.header > tr > th:eq(" + i + "), thead.header-copy > tr > th:eq(" + i + ")").css({
                        width: w
                    });
                });
                o.find("thead.header-copy").css({
                    margin: "0 auto",
                    width: o.width(),
                    "background-color": config.bgColor
                });
                return processScroll();
            });
        };
    })(jQuery);

}).call(this);


$(document).ready(function() {
    // make the header fixed on scroll
    $('.table-fixed-header').fixedHeader();
});

</script>
</head>
<body>
<div >
<form id="myForm" action="<%=request.getContextPath() %>/query/again"  method="post">
<textarea name="sql" rows="5" cols="120">
	${sql }
</textarea>
<br/>
<input type="button" value="gl" onclick="commitQuery('gl')"/>
<input type="button" value="tz" onclick="commitQuery('tz')"/>
<input type="button" value="jk" onclick="commitQuery('jk')"/>
<input type="button" value="test" onclick="commitQuery('mysql')"/>
<table class="table table-bordered table-striped  table-condensed table-fixed-header" width="1000px">
	<thead class="header">
	<tr>
		<td></td>
		<c:forEach items="${titles }" var="title">
			<th>${title }</th>
		</c:forEach>
	</tr>
	</thead>
	<tbody>
	<c:choose>
		<c:when  test="${fn:length(list) > 0 }">
			<c:forEach items="${list }" var="item" varStatus="var">
			<tr>
				<td>${var.index + 1 }</td>
				<c:forEach items="${item }" var="map">
					<td>${map.value }</td>
				</c:forEach>
			</tr>
		</c:forEach>
		</c:when>
		<c:when  test="${fn:length(list) == 0 }">
			<tr>
				<c:choose>
					<c:when test="${message == '' or message == null }">
						<td>无记录</td>
					</c:when>
					<c:otherwise>
						<td>${message }</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:when>
	</c:choose>
	</tbody>
</table>
</div>
</form>
</body>
</html>
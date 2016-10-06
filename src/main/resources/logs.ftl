<#include "common/header.ftl">
<#if logsList?? && logsList?size gt 0>
<table>
    <caption>
        <th>Time</th>
        <th>Type</th>
        <th>Message</th>
    </caption>
    <#list logsList as log>
        <tr>
            <#include "logItem.ftl">
        </tr>
    </#list>
</table>
<#else>
No logs in db
</#if>
<#include "common/footer.ftl">

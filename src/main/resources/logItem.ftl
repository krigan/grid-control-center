<td style="width: 25%">
${log.logDateFormatted}
</td>
<#switch log.logType.name()>
    <#case "INFO"> <#-- note the quotes -->
    <td style="color: green; width: 10%">
        <#break>
    <#case "INFO">
    <td style="color: red; width: 10%">
</#switch>
${log.logType.name()}
</td>
<td style="width: 65%">
${log.message}
</td>

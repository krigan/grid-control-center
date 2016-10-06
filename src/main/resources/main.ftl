<#include "common/header.ftl">
<div id="info">
<#if hub.running>
    Hub launched with command: <b>${hub.startCommand}</b>
    <input type="button" value="Stop hub" onclick="stopHub()">
<#else>
    Hub not launched yet
    <form id="hubForm"
          onsubmit="startHub(); return false;"
          action=""
          name="hubForm"
          method="get">
        <input type="text"
               id="hubParams"
               name="params"
               value="java -jar selenium-server-standalone-2.52.0.jar -role hub -port 4999"
               style="width: 600px">
        <input type="submit" title="Start hub" value="Start hub"/>
    </form>
</#if>
    <br>
<#if nodes?size gt 0>
    <ul>
        <#list nodes as node>
        <#assign nodeId = node?index>
        <#include "node.ftl">
    </#list>
    </ul>
<#else>
    <p>Nodes not launched yet</p>
</#if>
</div>
<#include "common/footer.ftl">
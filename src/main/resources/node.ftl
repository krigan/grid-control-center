<li>
<#if node.running>
    Node is running with params: <b>${node.startCommand}</b>
    <input type="button" value="Stop node" onclick="stopNode('${node.host}', '${node.port?c}')"/>
<#else>
    <form id="nodeForm_${nodeId}"
          onsubmit="startNode('${node.host}', '${node.port?c}', 'nodeForm_${nodeId}'); return false;"
          method="get"
          action="">
        <input type="text"
               id="${nodeId}"
               value="java -jar selenium-server-standalone-2.52.0.jar -role node -timeout 600 -browserTimeout 600 -port 4998 -hub http://LVR51819:4999/grid/register/"
               style="width: 1000px"/>
        <input type="submit" title="Start node" value="Start node"/>
    </form>
</#if>
</li>
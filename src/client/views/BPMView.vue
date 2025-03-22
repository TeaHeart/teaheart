<template>
  <div>
    <el-form>
      <el-form-item label="subject">
        <el-input v-model="data.start.data.subject" />
      </el-form-item>
      <el-form-item label="调出银行名称">
        <el-input v-model="data.start.data.data.formmain_0019['调出银行名称']" />
      </el-form-item>
      <el-form-item label="付款方账户号">
        <el-input v-model="data.start.data.data.formmain_0019['付款方账户号']" />
      </el-form-item>
      <el-form-item label="收款信息-金额">
        <el-input v-model="data.start.data.data.formson_0020[0]['收款信息-金额']" />
      </el-form-item>
      <el-form-item label="附件">
        <FileUploadComponent v-model:file-id-list="data.start.data.attachments" />
      </el-form-item>
      <el-form-item>
        <el-button @click="start" type="primary">start</el-button>
        <el-button @click="stop" type="danger">stop</el-button>
        <el-button @click="repeal" type="danger">repeal</el-button>
        <el-button @click="diagramImg" type="primary">diagramImg</el-button>
        <el-button @click="addNode" type="primary">addNode</el-button>
        <el-button @click="deleteNode" type="danger">deleteNode</el-button>
        <el-button @click="freeReplaceNode" type="danger">freeReplaceNode</el-button>
        <el-button @click="replaceItem" type="danger">replaceItem</el-button>
        <el-button @click="finish" type="primary">finish</el-button>
        <el-button @click="takeBack" type="warning">takeback</el-button>
        <el-button @click="stepBack" type="warning">stepBack</el-button>
        <el-button @click="specifyback" type="warning">specifyback</el-button>
      </el-form-item>
      <el-form-item label="processId">
        <el-input v-model="processId" />
      </el-form-item>
      <el-form-item label="workitemId">
        <el-input v-model="workitemId" />
      </el-form-item>
      <el-form-item label="nodeId">
        <el-input v-model="nodeId" />
      </el-form-item>
    </el-form>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-image :src="data.imageUrl" v-if="data.imageUrl" />
      </el-col>
      <el-col :span="16">
        <el-form-item v-for="(value, key) in result" :key="key" :label="key">
          {{ value }}
        </el-form-item>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { ElImage, ElButton, ElForm, ElFormItem, ElInput, ElRow, ElCol } from 'element-plus'
import FileUploadComponent from '../components/FileUploadComponent.vue'
import bpmApi from '../api/bpm.js'

const userMap = {
  user: '-1186211624194520416',
  a: '3816330436727292670',
}

const processId = computed({
  get: () => result.value.start.data.processId,
  set: (value) => (result.value.start.data.processId = value),
})

const workitemId = computed({
  get: () => result.value.start.data.workitems[0].id,
  set: (value) => (result.value.start.data.workitems[0].id = value),
})

const nodeId = computed({
  get: () => result.value.start.data.workitems[0].nodeId,
  set: (value) => (result.value.start.data.workitems[0].nodeId = value),
})

const data = ref({
  start: {
    appName: 'collaboration',
    data: {
      templateCode: 'cwgs_form_master_001',
      draft: '0',
      attachments: [],
      relateDoc: '',
      subject: '标题1',
      data: {
        formmain_0019: {
          调出银行名称: 'aa',
          付款方账户号: 'bb',
        },
        formson_0020: [
          {
            '收款信息-金额': 100,
          },
        ],
        thirdAttachments: [],
      },
    },
  },
  stop: {
    appName: 'collaboration',
    workitemId: '{{workItemId}}',
    data: {
      stopOpinion: '终止',
    },
  },
  repeal: {
    appName: 'collaboration',
    workitemId: '',
    data: {
      stopOpinion: '撤销意见',
    },
  },
  imageUrl: '',
  addNode: {
    appName: 'collaboration',
    workitemId: '{{workItemId}}',
    changeType: '1',
    submitType: '1',
    data: {
      add_node_info: {
        userId: [userMap['user']],
        node_process_mode: 'all',
        policyId: '{{policyId}}',
        formOperationPolicy: '0',
        backToMe: '1',
        dealTerm: '2025-12-31 00:00',
        remindTime: '3600',
        flowType: '2',
      },
      comment_deal: {
        attitude: '1',
        content: '接口指定回退',
      },
    },
  },
  deleteNode: {
    appName: 'collaboration',
    activityIdList: '{{nodeId}}',
    workitemId: '{{workItemId}}',
    submitType: '1',
    data: {
      comment_deal: {
        attitude: '1',
        content: '接口指定回退',
      },
    },
  },
  freeReplaceNode: {
    appName: 'collaboration',
    activityId: '{{nodeId}}',
    processId: '{{processId}}',
    caseId: '-1',
    data: {
      userId: userMap['a'],
    },
  },
  replaceItem: {
    appName: 'collaboration',
    workitemId: '{{workItemId}}',
    nextMemberId: userMap['a'],
  },
  finish: {
    appName: 'collaboration',
    workitemId: '{{workItemId}}',
    data: {
      comment_deal: {
        attitude: '1',
        comment: '已阅',
      },
    },
  },
  takeback: {
    appName: 'collaboration',
    workitemId: '{{workItemId}}',
    data: {
      isSaveOpinion: 'true',
    },
  },
  stepBack: {
    appName: 'collaboration',
    workitemId: '{{workItemId}}',
    data: {
      isWFTrace: '0',
      comment_deal: {
        attitude: '1',
        content: '回退意见',
      },
    },
  },
  specifyback: {
    appName: 'collaboration',
    workitemId: '{{workItemId}}',
    targetNodeId: '{{nodeId}}',
    data: {
      isWFTrace: '0',
      comment_deal: {
        attitude: '1',
        content: '指定回退意见',
      },
    },
  },
})

const result = ref({
  start: {
    code: 0,
    data: {
      workitems: [
        {
          nodeName: '',
          userLoginName: '',
          id: '',
          userName: '',
          nodeId: '',
          userId: '',
        },
      ],
      app_bussiness_data: { affairId: '', summaryId: '' },
      processId: '',
      subject: '',
      errorMsg: '',
    },
    message: '',
  },
})

async function diagramImg() {
  const blob = await bpmApi.processDiagramImg({
    processId: processId.value,
    workitemId: workitemId.value,
  })
  const imageUrl = URL.createObjectURL(blob)
  data.value.imageUrl = imageUrl
}

async function start() {
  const json = await bpmApi.processStart(data.value.start)
  const resData = json.data.data
  resData.app_bussiness_data = JSON.parse(resData.app_bussiness_data)
  result.value.start = json.data
  diagramImg()
}

async function stop() {
  data.value.stop.workitemId = workitemId
  const json = await bpmApi.processStop(data.value.stop)
  result.value.stop = json.data
  diagramImg()
}

async function repeal() {
  data.value.repeal.workitemId = workitemId
  const json = await bpmApi.processRepeal(data.value.repeal)
  result.value.repeal = json.data
  diagramImg()
}

async function addNode() {
  data.value.addNode.workitemId = workitemId
  const json = await bpmApi.processAddNode(data.value.addNode)
  result.value.addNode = json.data
  diagramImg()
}

async function deleteNode() {
  data.value.deleteNode.workitemId = workitemId
  data.value.deleteNode.activityIdList = nodeId
  const json = await bpmApi.processDeleteNode(data.value.deleteNode)
  result.value.deleteNode = json.data
  diagramImg()
}

async function freeReplaceNode() {
  data.value.freeReplaceNode.activityId = nodeId
  data.value.freeReplaceNode.processId = processId
  const json = await bpmApi.processFreeReplaceNode(data.value.freeReplaceNode)
  result.value.freeReplaceNode = json.data
  diagramImg()
}

async function replaceItem() {
  data.value.replaceItem.workitemId = workitemId
  const json = await bpmApi.processReplaceItem(data.value.replaceItem)
  result.value.replaceItem = json.data
  diagramImg()
}

async function finish() {
  data.value.finish.workitemId = workitemId
  const json = await bpmApi.workitemFinish(data.value.finish)
  result.value.finish = json.data
  diagramImg()
}

async function takeBack() {
  data.value.takeback.workitemId = workitemId
  const json = await bpmApi.workitemTakeBack(data.value.takeback)
  result.value.takeback = json.data
  diagramImg()
}

async function stepBack() {
  data.value.stepBack.workitemId = workitemId
  const json = await bpmApi.workitemStepBack(data.value.stepBack)
  result.value.stepBack = json.data
  diagramImg()
}

async function specifyback() {
  data.value.specifyback.workitemId = workitemId
  data.value.specifyback.targetNodeId = nodeId
  const json = await bpmApi.workitemSpecifyback(data.value.specifyback)
  result.value.specifyback = json.data
  diagramImg()
}
</script>

<style scoped></style>

<template>
  <div>
    <el-upload :action="action" multiple :show-file-list="false" @success="success">
      <template #trigger>
        <el-button type="primary">upload</el-button>
      </template>
      <template #default>
        <el-button @click="clear">clear</el-button>
      </template>
    </el-upload>
    <ul>
      <li v-for="item in fileList" :key="item.id">
        <el-link :href="item.url" target="_blank"> {{ item.file }} {{ item.id }} </el-link>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElUpload, ElButton, ElLink } from 'element-plus'

const action = `${import.meta.env.VITE_SERVER_URL}/api/file/upload`

defineProps({
  fileIdList: {
    type: Array,
    required: true,
  },
})

const emit = defineEmits(['update:fileIdList'])

function update() {
  emit(
    'update:fileIdList',
    fileList.value.map((item) => item.id),
  )
}

function clear() {
  fileList.value.splice(0)
  update()
}

const fileList = ref([])

function success(json) {
  console.log(json)
  let info = json.data.atts[0]
  if (!info) {
    throw new Error('file info not found')
  }
  const file = {
    id: info.fileUrl,
    file: info.filename,
    url: `${import.meta.env.VITE_SEEYON_URL}/seeyon/rest/attachment/file/${info.fileUrl}?fileName=${info.filename}`,
  }
  console.log(JSON.stringify(file))
  fileList.value.push(file)
  update()
}
</script>

<style scoped></style>

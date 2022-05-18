<template>
<div>
  <v-card elevation="0"
      color="secondary">
    <v-card-title class="text-center justify-center py-6">
      <h1 class="font-weight-bold text-h2">
        BOARD
      </h1>
    </v-card-title>

    <v-tabs
      v-model="tab"
      grow
    >
      <v-tab
        v-for="tabItem in tabItems"
        :key="tabItem.tabTitle"
        class="primary--text"
      >
        {{ tabItem.tabTitle }}
      </v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab" >
      <v-tab-item
        v-for="tabItem in items"
        :key="tabItem.tabTitle"
      >
          <v-card
    width="100%"
    class="mx-auto"
    elevation="0" 
  >
    <v-list three-line>
      <template v-for="(item, index) in items" >
        <v-subheader
          v-if="item.header"
          :key="item.header"
          v-text="item.header"
          class="justify-center"
        ></v-subheader>

        <v-divider
          v-else-if="item.divider"
          :key="index"
          :inset="item.inset"
        ></v-divider>

        <v-list-item
          v-else
          :key="index"
        >
          <v-list-item-content flex-row>
            <v-list-item-title v-html="item.title"></v-list-item-title>
            <v-list-item-subtitle  inline  v-html="item.writer"></v-list-item-subtitle>
            <v-list-item-subtitle inline v-html="item.date"></v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </template>
    </v-list>
  </v-card>
      </v-tab-item>
    </v-tabs-items>
  </v-card>
    <!-- <v-btn>글쓰기</v-btn>
    <p>글 삭제는 로그인을 하신 후 가능합니다.</p> -->
  </div>
</template>

<script>
  export default {
    data: () => ({
      tab: null,
        tabItems: [ {
          tabTitle: '문의게시판', 
          text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
        }, {
          tabTitle:    'QnA', 
          text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
        }
        ],
      items: [
        { header: 'CS 운영시간: 9시~17시, 문의량이 많아 답변이 늦어질 수 있는 점, 양해 부탁드립니다.' },
        {
          index:1,
          title: '배송문의',
          writer: '이이이',
          date: '2023-01-15',
        },
        { divider: true, inset: true },
        {
          index:2,
          title: '배송지문의',
          writer: '박박박',
          date: '2023-01-15',
        },
        { divider: true, inset: true },
        {
          index:3,
          title: '환불문의',
          writer: '최최최',
          date: '2023-01-15',
        },
        { divider: true, inset: true },
        {
          index:4,
          title: '문의',
          writer: '정정정',
          date: '2023-01-15',
        },
        { divider: true, inset: true },
        {
          index:5,
          title: '배송문의',
          writer: '김김김',
          date: '2023-01-15',
        },
      ],
    }),
  }
</script>
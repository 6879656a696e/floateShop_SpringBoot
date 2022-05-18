<template>
  <v-card
    class="mx-auto pt-5"
    width="100%"
    elevation="0"
    color="secondary"
  >

  <v-rating
  readonly
  color="orange"
  background-color="orange"
  hover
  length="5"
  size="64"
  v-model="totalRating"
  class="text-center"
></v-rating>

    <v-list two-line 
    color="secondary">
      <v-list-item-group
        multiple
      >
        <template v-for="(item, index) in items">
          <v-list-item :key="item.title">
            <template>
              <v-list-item-content>
                <v-list-item-title v-text="item.title"></v-list-item-title>

                <v-list-item-subtitle
                  class="text--primary"
                  v-text="item.headline"
                ></v-list-item-subtitle>

                <v-list-item-subtitle v-text="item.subtitle"></v-list-item-subtitle>
              </v-list-item-content>

              <v-list-item-action secondary>
                <v-list-item-action-text v-text="item.action"></v-list-item-action-text>

            <div>
                <v-rating
                readonly
                color="orange"
                background-color="orange"
                size="30"
                length="5"
                :value="item.ratings"
                ></v-rating>
                </div>
              </v-list-item-action>
            </template>
          </v-list-item>

          <v-divider
            v-if="index < items.length - 1"
            :key="index"
          ></v-divider>
        </template>
      </v-list-item-group>
    </v-list>
  </v-card>
</template>
<script>
  export default {
    data: () => ({
    totalRating: 4,
      items: [
        {
          action: '15 min',
          headline: '조아요조아요조아요',
          subtitle: `배송빠름`,
          title: 'Ali Connors',
          total: 5,
          ratings: 4,
        },
        {
          action: '2 hr',
          headline: '갠춘',
          subtitle: `갠춘`,
          title: 'Scrott',
          total: 5,
          ratings: 4,
        },
        {
          action: '6 hr',
          headline: '선물용으로 구매',
          subtitle: '조아요조아요',
          title: 'Sandra Adams',
          total: 5,
          ratings: 5,
        },
        {
          action: '12 hr',
          headline: 'ㄴㅇㄹㄴㅇㄹㄴㅇㄹ',
          subtitle: 'ㅣㅏㅓㅣㄹ아너닐아ㅓㄴㄹ이ㅏㅓㄹㄴㅇ',
          title: 'Trevor Hansen',
          total: 5,
          ratings: 5,
        },
        {
          action: '18hr',
          headline: '배송비 비쌈',
          subtitle: '8만원 이상 구매 에바',
          title: 'Britta Holt',
          total: 5,
          ratings: 2,
        },
      ],
    }),
    method: {

    }
  }
</script>
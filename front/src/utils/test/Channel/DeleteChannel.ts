import { deleteChannel } from "api/Channel";
import { DeleteChannelParams } from "models/Channel.interface";

export function func(lst: any): any {
  console.log("test API");
  for (const elem of lst) {
    console.log("Test data is....", elem);
    const results = deleteChannel(elem);
    results.then((d) => {
      console.log("Result is...", d);
      // for (const i of d.content) {
      //   console.log(i);
      // }
    });
  }
}

const data1: DeleteChannelParams = {
  channelId: 26,
};

//무빙건으로 테스트 시 이건 FAIL이 떠야 정상
const data2: DeleteChannelParams = {
  channelId: 26,
};

//무빙건으로 테스트 시 이건 FAIL이 떠야 정상
const data3: any = {};

const data4: any = {};

export let datas: Array<any> = [
  data1,
  data2,
  // data3,
  // data4,
  // data5,
];

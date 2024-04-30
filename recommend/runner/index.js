import axios from "axios";

function sleep(millisec) {
  return new Promise((resolve) => setTimeout(resolve, millisec));
}

/**
 * 테스트 수행
 *
 * 유저는 1 ~ 100번 까지 초기에 들어 있음
 * 초기 설정 금액 30억, 추천으로 가입을 통해 1만원씩 지급 받을 수 있음
 */
async function main() {
  const client = axios.create({
    baseURL: "http://localhost:18080",
  });

  let interval = 100;

  while (interval > 0) {
    console.log(`테스트 수행 ===> interval: ${interval}`);
    await Promise.all(
      Array(100)
        .fill(undefined)
        .map((_) =>
          client.post("/api/v1/users", {
            userId: `${Math.random().toString()}`,
            password: 1234,
            recommendUserId: "User1",
          })
        )
    );

    interval--;

    const remainBudget = await client.get("/api/v1/users/event/budget");
    console.log(`남은 이벤트 금액 ===> ${remainBudget.data}`);
  }
}

main();

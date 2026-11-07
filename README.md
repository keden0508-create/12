# Armor HUD (Fabric, Minecraft 26.3, client-only)

착용 중인 갑옷 4종과 양손 아이템의 **아이콘 + 내구도**를 화면 왼쪽에 표시합니다.

| 키 (기본) | 동작 |
|---|---|
| `Tab` | HUD 표시 + 상세정보(이름, 남은/최대 내구도, %) 열기 |
| `V`   | HUD 전체 숨김 |

키는 `설정 → 조작 → Armor HUD`에서 변경할 수 있습니다.
Tab은 바닐라 플레이어 목록 키와 겹치므로 불편하면 둘 중 하나를 바꾸세요.

## 요구 사항
- Minecraft 26.3, Fabric Loader >= 0.19.5, Fabric API >= 0.161.0
- JDK 25

## 빌드
```bash
gradle wrapper        # 또는 fabricmc.net/develop 템플릿의 gradle/wrapper 복사
./gradlew build       # build/libs/armor-hud-1.0.0.jar
./gradlew runClient   # 개발 환경 실행
```
`gradle.properties`의 `loom_version`, `fabric_version`이 맞지 않으면
<https://fabricmc.net/develop/> 의 값으로 교체하세요.

## 라이선스
MIT

## GitHub Actions
- `build.yml`: push / PR마다 빌드하고 jar를 아티팩트로 업로드
- `release.yml`: `git tag v1.0.0 && git push --tags` 하면 jar가 첨부된 Release 생성
  (태그와 별개로 `gradle.properties`의 `mod_version`도 맞춰 올리세요)

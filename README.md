

Clean Architecture là một cách thức tương đối tuyệt vời để triển khai các dự án, cho đến một ngày mình nhận ra với một số dòng dự án, nó quá phức tạp, tốn kém(về mặt effort cần thiết bỏ ra) khi áp dụng cho các ứng dụng nhiều module, nhiều feature. 
Khi ứng dụng tăng lên đến 80-90 features/modules hoặc hơn thế nữa chẳng hạn, hãy thực sự nghiêm túc suy nghĩ về vấn đề này.
Với các ứng dụng ko thiên về business logic, ví dụ như các ứng dụng client server đơn thuần, nhiều screen flow, nhiều feature thêm vào nhiều module cắm thêm.. Clean architecture tuy có lợi cho phase maintain nhưng lại lấy đi tương đối effort  trong phase develop. 
Và mình cần tìm một giải pháp khác cân bằng hơn, tất nhiên, ko có viên đạn bạc nào, mô hình nào cũng có điểm mạnh và điểm yếu. Chúng ta đơn giản chỉ là lựa chọn thứ phù hợp cho dự án của mình - just only

Có 1 số điểm chúng ta suy nghĩ kỹ, và tìm ra phương án giải quyết trước khi thực sự quyết định dựng lên một high level design hoặc chọn một architecture pattern nào đó

 1. Việc chuyển đổi màn hình diễn ra như thế nào?
 2. Các feature/module làm việc với nhau như thế nào?
 3. Việc chia sẻ các thành phần dùng chung sẽ như thế nào?
 4. Architecture pattern lựa chọn cho các thành phần sẽ là gì?
 5. Có thể/có nên tạo ra abstraction layer ko?
 6. etc..
  
Khi đề xuất một architecture mới, chúng ta thường kỳ vọng nó giải quyết được các vấn đề mà các mô hình cũ gặp phải, cách tốt nhất là bạn nên liệt kê, gạch đầu dòng ra giấy, và cân nhắc xử lý từng issue một trên architecture mới, nếu nó hoàn toàn xử lý được hết thì chứng tỏ architecture mới dựng lên có thể sẽ adapt được bài toán của bạn. Nếu còn bất cứ vấn đề nào ko xử lý được, hãy cân nhắc thật cẩn thận một lần nữa bởi đôi khi một lỗ thủng nhỏ có thể làm chìm cả một con tàu lớn
 
## Việc chuyển đổi/navigate các screen diễn ra thế nào?
1. **Hạn chế sử dụng Navigation Graph trên xml**
Tương tự với Navigation Graph trên Android, lập trình viên IOS đã quen thuộc với StoryBoard từ những năm 2011 - 2012, tuy nhiên họ hiếm khi sử dụng full StoryBoard trong những dự án nhiều màn hình và có các screen phức tạp, bởi vì việc add quá nhiều screen lên StoryBoard sẽ khiến Xcode trở lên ì ạch, giảm performance của lập trình viên ngoài ra còn rất khó để control và chỉnh sửa nếu số lượng màn hình quá lớn
Android cũng gặp vấn đề tương tự với Navigation Graph, khi số lượng màn hình add vào Navigation Graph(xml file) quá lớn, Android Studio sẽ trở lên rất chậm 
![enter image description here](https://developer.android.com/static/images/topic/libraries/architecture/navigation-graph_2x-callouts.png)

Nên nếu được, chúng ta hãy cố gắng hạn chế sử dụng phương thức này để tạo các Navigation Graph, thay vào đó hãy thử tạo [Navigation graph bằng code](https://developer.android.com/guide/navigation/navigation-kotlin-dsl)

```kotlin
val navController = findNavController(R.id.nav_host_fragment)
navController.graph = navController.createGraph
(
	startDestination = nav_routes.home
) {    
		fragment<HomeFragment>(nav_routes.home) {
		        label = resources.getString(R.string.home_title)  
		}
		fragment<PlantDetailFragment>(${nav_routes.plant_detail}/${nav_arguments.plant_id}) {
		        label = resources.getString(R.string.plant_detail_title)
		        argument(nav_arguments.plant_id) {
			        type = NavType.StringType        
			    }    
			}
		}
```
**2. Sử dụng deep link khi chuyển đổi giữa các screen**
Với cách sử dụng Navigation component thông thường, để chuyển đổi các screen thì cần biết ID và params gửi đến screen tiếp theo, nhưng với mô hình multi-features, các screen được nằm ở các features khác nhau và hoàn toàn ko biết đến các screen khác nên trường hợp này chúng ta sử dụng **[deeplink](https://developer.android.com/guide/navigation/navigation-deep-link)** để chuyển đổi các màn hình
Khai báo khởi tạo NavGraph & deeplink [(Kotlin DSL)](https://developer.android.com/guide/navigation/navigation-kotlin-dsl)
```kotlin
inline fun NavController.graphScreenMovieDetail(): NavGraph {  
    return createGraph(  
        startDestination = MovieDetailScreenRouter.startScreen,  
  ) {  
  fragment<MovieDetailFragment>(MovieDetailScreenRouter.startScreen,) {  
  label = "movie Detail"  
  deepLink {  
		  uriPattern = "android-app://com.baka3k.test.feature.moviedetail.router/moviedetailfragment?idmovie={idmovie}&name={name}&poster={poster}"  
		}  
	 }
   }
 }
```
khi muốn chuyển screen tiếp theo
```kotlin
val params = mapOf(  
    "idmovie" to movieID,  
  "name" to name,  
  "poster" to poster  
)
var uri = "android-app://com.baka3k.test.feature.moviedetail.router/moviedetailfragment".toUriWithParams(params)  
findNavController().navigate(uri)
```
## Các feature/module làm việc với nhau như thế nào?
Rule **bắt buộc** cần tuân theo đó là các feature/module sẽ **không** được phép làm việc trực tiếp với nhau, vd: feature movie không làm việc trực tiếp với feature actor, feature chuyển tiền ko làm việc trực tiếp với feature xem danh sách giao dịch..etc
Việc tuân theo rule này sẽ khiến các feature/module có thể tách rời, đảm bảo Loose Coupling, trong quá trình maintain khả năng thay thế feature A thành feature A' , hoặc việc xóa bớt các features  ko dùng sẽ trở nên dễ dàng hơn.

## Việc chia sẻ các thành phần dùng chung sẽ như thế nào?
Câu hỏi này xuất phát từ một tình huống thực tế, khi 2 features cần dùng một chức năng mà đối phương nắm giữ, feature A cần chức năng B của Feature B, feature B cần chức năng A của feature A. 
Ví dụ: khi ở feature 'MovieDetail' cần gọi chức năng 'sửa tên diễn viên' nằm trong feature 'Actor', và feature 'Actor' lại cần gọi chức năng 'sửa tên phim' nằm trong feature 'MovieDetail' chả hạn - khi đó chúng ta xuất hiện tình huống phụ thuộc vòng (Circular dependency)

Key point ở đây là: **Loại bỏ phụ thuộc vòng**
![enter image description here](https://i.ibb.co/pjBmpdj/circular-dependency.png)
Chúng ta sẽ tạo ra 1 số module trung gian để loại bỏ circular dependency 

## Architecture pattern lựa chọn cho các thành phần sẽ là gì?
Với mô hình multi-features, mỗi feature là một đơn vị độc lập tách biệt nên nó cần có architecture pattern cho riêng mình, với Android/Flutter hoặc các dòng mobile nói chung mình thiên về sử dụng MVVM vì dự đơn giản & dễ triển khai tận dụng tối đa các component được hỗ trợ sẵn như viewmodel, stateflow..etc

## Có thể/có nên tạo ra abstraction layer ko?
Abstraction layer là điểm đặc biệt nhất và là keypoint để phân biệt Clean Architecture với các kiến thúc khác. Vậy có thể tạo ra Abstraction layer cho Multi-features được ko?
Câu trả lời là được, nhưng nó thực sự quá phức tạp triển khai dự án, coding effort trong develop phase có thể tăng lên rất nhiều, nên mình đã loại bỏ Abstraction Layer trong kiến trúc này, và chỉ giữ lại UseCase - 'abstraction' phụ trách business logic đặt trong từng feature

![enter image description here](https://i.ibb.co/Z6dq7xN/featurecontent.png)

## Sử dụng plugin cho các feature có chung mục đích phát triển

Đầu tiên chúng ta quan sát các core feature như hình dưới

![enter image description here](https://i.ibb.co/NYYBDMk/package.png)

Hầu hết các gói core này đều cung cấp 1 chức năng gì đó dùng chung cho các feature, ví dụ core-ui cung cấp các UI dùng chung, core-database cung cấp loại database như room, sqlite..etc, core-datastore cung cấp nơi lưu cấu hình, config...etc và đều được sử dụng tại các feature. Nói đơn giản đây là source base của feature trong kiến trúc multi-feature. 
Để tránh việc config của feature quá rườm rà và để optimize job build chúng ta sử dụng thêm plugin, các feature sử dụng plugin này sẽ tuân theo một khung do plugin quy dịnh. 

Trước khi sử dụng plugin

![enter image description here](https://i.ibb.co/DtDybjj/without-Plugin.png)

Sau khi sử dụng plugin

![enter image description here](https://i.ibb.co/865Ynh0/with-Plugin.png)

Các khai báo rườm rà như target, compile, dependency đã include và xử lý trong plugin, việc khai báo sẽ trở nên rất gọn gàng như trên
```
id(hi.android.library)
id(hi.android.features)
```
Những feature khai báo plugin sẽ tự động được include các project như core-ui, core-network, core-database...etc, tức là chúng ta sẽ ko cần manual khai báo implementation(":core-ui")... ở từng feature nữa. 
Custom plugin sẽ trở thành 1 cái khung/base chương trình cho feature sử dụng nó


## Sử dụng mix các loại feature truyền thống(fragment/view/binding) và các feature sử dụng Jetpack compose
(TBD)
## Sử dụng các mix với Dynamic-Features
(TBD) 

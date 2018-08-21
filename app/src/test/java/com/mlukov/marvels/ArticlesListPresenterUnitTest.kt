package com.mlukov.marvels

import com.mlukov.marvels.api.model.ArticleApiData
import com.mlukov.marvels.domain.interactors.ArticleInteractor
import com.mlukov.marvels.domain.models.ArticleData
import com.mlukov.marvels.domain.models.ArticleDataList
import com.mlukov.marvels.domain.repositories.marvels.IMarvelsRepository
import com.mlukov.marvels.domain.repositories.local.ILocalStorageRepository
import com.mlukov.marvels.presentation.articles.list.model.ArticleViewData
import com.mlukov.marvels.presentation.articles.list.presenter.ArticlesListPresenter
import com.mlukov.marvels.presentation.articles.list.view.IArticlesListView
import com.mlukov.marvels.presentation.articles.list.model.ArticleListViewModel
import com.mlukov.marvels.presentation.providers.INetworkInfoProvider
import com.mlukov.marvels.presentation.providers.IResourceProvider
import com.mlukov.marvels.utils.ISchedulersProvider

import org.junit.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer

import java.util.ArrayList
import java.util.Date

import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.doReturn

class ArticlesListPresenterUnitTest {

    @Mock
    private val articlesView: IArticlesListView? = null

    @Mock
    val resourceProvider : IResourceProvider? = null

    @Mock
    val networkInfoProvider : INetworkInfoProvider ? = null

    @Mock
    private val localStorageRepository: ILocalStorageRepository? = null

    @Mock
    private val mMarvelsRepository: IMarvelsRepository? = null

    @Mock
    private val articlesInteractorMock: ArticleInteractor? = null

    @Mock
    private val schedulersProvider: ISchedulersProvider? = null

    @InjectMocks
    private val articlesPresenter: ArticlesListPresenter? = null

    @InjectMocks
    private val articlesInteractor: ArticleInteractor? = null

    private val testScheduler = TestScheduler()

    private val cachedItems = ArticleDataList()
    private val serverItems = mutableListOf<ArticleApiData>()

    private val articleItems = mutableListOf<ArticleViewData>()

    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)

        doReturn(testScheduler).`when`<ISchedulersProvider>(schedulersProvider).ioScheduler()
        doReturn(testScheduler).`when`<ISchedulersProvider>(schedulersProvider).uiScheduler()
        doReturn(testScheduler).`when`<ISchedulersProvider>(schedulersProvider).computationScheduler()

        val articleApiData = ArticleApiData()

        articleApiData.id = 2
        articleApiData.title = "Title2"
        articleApiData.subtitle = "Subtitle2"
        articleApiData.date = Date("1982/01/21 18:41")

        val articleData = ArticleData()
        articleData.id = 1
        articleData.title = "TITLE1"
        articleData.subtitle = "SUBTTITLE1"
        articleData.date = Date("1980/01/21 18:41")

        val cachedData = mutableListOf<ArticleData>()
        cachedData.add(articleData)
        cachedItems.articles = cachedData
        serverItems.add(articleApiData)
    }

    @Test
    fun Should_Load_Articles_List_Into_View() {

        doReturn(Single.just(cachedItems)).`when`<ILocalStorageRepository>(localStorageRepository).getArticleDataListFromCache()
        doReturn(Single.just<List<ArticleApiData>>(serverItems)).`when`<IMarvelsRepository>(mMarvelsRepository).getArticleList()
        doAnswer(object : Answer<Single<ArticleDataList>> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ArticleDataList> {
                return articlesInteractor!!.getArticleList()
            }
        }).`when`<ArticleInteractor>(articlesInteractorMock).getArticleList()

        doAnswer(object : Answer<ArrayList<ArticleViewData>> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): ArrayList<ArticleViewData>? {

                val arrayList = invocation.getArgument<ArrayList<ArticleViewData>>(0)
                articleItems.clear()
                articleItems.addAll(arrayList)
                return null
            }
        }).`when`<IArticlesListView>(articlesView).onArticlesLoaded( Mockito.any() )

        articlesPresenter!!.loadArticles(false)
        testScheduler.triggerActions()

        val repositoryItem = articleItems[0]

        Assert.assertNotNull(repositoryItem)
    }

    @Test
    fun Should_Load_Articles_List_From_Cache() {

        doReturn(Single.just(cachedItems)).`when`<ILocalStorageRepository>(localStorageRepository).getArticleDataListFromCache()
        doReturn(Single.just<List<ArticleApiData>>(serverItems)).`when`<IMarvelsRepository>(mMarvelsRepository).getArticleList()
        doAnswer(object : Answer  <Single<ArticleDataList>> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock):  Single<ArticleDataList> {
                return articlesInteractor!!.getArticleList()
            }
        }).`when`<ArticleInteractor>(articlesInteractorMock).getArticleList()

        doAnswer(object : Answer < ArticleListViewModel> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): ArticleListViewModel? {

                val arrayList = invocation.getArgument<ArrayList<ArticleViewData>>(0)
                articleItems.clear()
                articleItems.addAll(arrayList)
                return null
            }
        }).`when`<IArticlesListView>(articlesView).onArticlesLoaded(Mockito.any())

        articlesPresenter!!.loadArticles(false)
        testScheduler.triggerActions()

        val repositoryItem = articleItems[0]

        Assert.assertEquals("Item not loaded from cache", "TITLE1", repositoryItem.title)
    }

    @Test
    fun Should_Load_Articles_List_From_Server_Refresh_True() {

        doReturn(Single.just(cachedItems)).`when`<ILocalStorageRepository>(localStorageRepository).getArticleDataListFromCache()
        doReturn(Single.just<List<ArticleApiData>>(serverItems)).`when`<IMarvelsRepository>(mMarvelsRepository).getArticleList()

        doAnswer(object : Answer <Any>{
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Any? {

                cachedItems.articles!!.clear()
                return null
            }
        }).`when`<ILocalStorageRepository>(localStorageRepository).dropArticleDataListCache()

        doAnswer(object : Answer <Single<ArticleDataList> > {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ArticleDataList>  {
                return articlesInteractor!!.getArticleList()
            }
        }).`when`<ArticleInteractor>(articlesInteractorMock).getArticleList()

        doAnswer(object : Answer <Any> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Any {
                return articlesInteractor!!.clearCache()
            }
        }).`when`<ArticleInteractor>(articlesInteractorMock).clearCache()

        doAnswer(object : Answer <Single<ArticleDataList> > {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ArticleDataList>   {

                val articles = invocation.getArgument<ArticleDataList>(0)
                return Single.just(articles)
            }
        }).`when`<ILocalStorageRepository>(localStorageRepository).addArticleDataListToCache(Mockito.any(ArticleDataList::class.java))

        doAnswer(object : Answer <ArrayList<ArticleViewData>> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): ArrayList<ArticleViewData>? {

                val arrayList = invocation.getArgument<ArrayList<ArticleViewData>>(0)
                articleItems.clear()
                articleItems.addAll(arrayList)
                return null
            }
        }).`when`<IArticlesListView>(articlesView).onArticlesLoaded(Mockito.any())

        articlesPresenter!!.loadArticles(true)
        testScheduler.triggerActions()

        val repositoryItem = articleItems[0]

        Assert.assertEquals("Item not loaded from cache", "TITLE1", repositoryItem.title)
    }

    @Test
    fun Should_Load_Articles_List_From_Server_Refresh_False_No_Cached_Items() {

        doReturn(Single.just(ArticleDataList.empty())).`when`<ILocalStorageRepository>(localStorageRepository).getArticleDataListFromCache()
        doReturn(Single.just<List<ArticleApiData>>(serverItems)).`when`<IMarvelsRepository>(mMarvelsRepository).getArticleList()

        doAnswer(object : Answer <Any> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Any? {

                cachedItems.articles!!.clear()
                return null
            }
        }).`when`<ILocalStorageRepository>(localStorageRepository).dropArticleDataListCache()

        doAnswer(object : Answer <Any> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Any {
                return articlesInteractor!!.getArticleList()
            }
        }).`when`<ArticleInteractor>(articlesInteractorMock).getArticleList()

        doAnswer(object : Answer <Any> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Any {
                return articlesInteractor!!.clearCache()
            }
        }).`when`<ArticleInteractor>(articlesInteractorMock).clearCache()

        doAnswer(object : Answer <Single<ArticleDataList> >{
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ArticleDataList>  {

                val articles = invocation.getArgument<ArticleDataList>(0)
                return Single.just(articles)
            }
        }).`when`<ILocalStorageRepository>(localStorageRepository).addArticleDataListToCache(Mockito.any(ArticleDataList::class.java))

        doAnswer(object : Answer <ArrayList<ArticleViewData>> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): ArrayList<ArticleViewData>? {

                val arrayList = invocation.getArgument<ArrayList<ArticleViewData>>(0)
                articleItems.clear()
                articleItems.addAll(arrayList)
                return null
            }
        }).`when`<IArticlesListView>(articlesView).onArticlesLoaded(Mockito.any())

        articlesPresenter!!.loadArticles(false)
        testScheduler.triggerActions()

        val repositoryItem = articleItems[0]

        Assert.assertEquals("Item not loaded from cache", "Title2", repositoryItem.title)
    }

    @Test
    fun Verify_Method_Calls_When_Loading_From_Cache() {

        doReturn(Single.just(cachedItems)).`when`<ILocalStorageRepository>(localStorageRepository).getArticleDataListFromCache()
        doReturn(Single.just<List<ArticleApiData>>(serverItems)).`when`<IMarvelsRepository>(mMarvelsRepository).getArticleList()
        doAnswer(object : Answer <Single<ArticleDataList> > {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ArticleDataList> {
                return articlesInteractor!!.getArticleList()
            }
        }).`when`<ArticleInteractor>(articlesInteractorMock).getArticleList()

        doAnswer(object : Answer <ArrayList<ArticleViewData>> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): ArrayList<ArticleViewData>? {

                val arrayList = invocation.getArgument<ArrayList<ArticleViewData>>(0)
                articleItems.clear()
                articleItems.addAll(arrayList)
                return null
            }
        }).`when`<IArticlesListView>(articlesView).onArticlesLoaded(Mockito.any())

        articlesPresenter!!.loadArticles(false)
        testScheduler.triggerActions()

        Mockito.verify<ArticleInteractor>(articlesInteractorMock, Mockito.times(1)).getArticleList()
        Mockito.verify<ArticleInteractor>(articlesInteractorMock, Mockito.times(0)).clearCache()
        Mockito.verify<ILocalStorageRepository>(localStorageRepository, Mockito.times(1)).getArticleDataListFromCache()
        Mockito.verify<IArticlesListView>(articlesView, Mockito.times(2)).onLoadingStateChange(Mockito.anyBoolean())
        Mockito.verify<IArticlesListView>(articlesView, Mockito.times(1)).onArticlesLoaded(Mockito.any())
    }

    @Test
    fun Verify_Method_Calls_When_Loading_From_Server_Refresh_True() {

        doReturn(Single.just(cachedItems)).`when`<ILocalStorageRepository>(localStorageRepository).getArticleDataListFromCache()
        doReturn(Single.just<List<ArticleApiData>>(serverItems)).`when`<IMarvelsRepository>(mMarvelsRepository).getArticleList()

        doAnswer(object : Answer <Single<ArticleDataList> > {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ArticleDataList> ? {

                cachedItems.articles!!.clear()
                return null
            }
        }).`when`<ILocalStorageRepository>(localStorageRepository).dropArticleDataListCache()

        doAnswer(object : Answer <Single<ArticleDataList> > {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ArticleDataList>  {
                return articlesInteractor!!.getArticleList()
            }
        }).`when`<ArticleInteractor>(articlesInteractorMock).getArticleList()

        doAnswer(object : Answer <Any> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Any {
                return articlesInteractor!!.clearCache()
            }
        }).`when`<ArticleInteractor>(articlesInteractorMock).clearCache()

        doAnswer(object : Answer <Single<ArticleDataList> > {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ArticleDataList>  {

                val articleDataList = invocation.getArgument<ArticleDataList>(0)
                return Single.just(articleDataList)
            }
        }).`when`<ILocalStorageRepository>(localStorageRepository).addArticleDataListToCache(Mockito.any(ArticleDataList::class.java))

        doAnswer(object : Answer <ArrayList<ArticleViewData>> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): ArrayList<ArticleViewData>? {

                val arrayList = invocation.getArgument<ArrayList<ArticleViewData>>(0)
                articleItems.clear()
                articleItems.addAll(arrayList)
                return null
            }
        }).`when`<IArticlesListView>(articlesView).onArticlesLoaded(Mockito.any())

        articlesPresenter!!.loadArticles(true)
        testScheduler.triggerActions()

        Mockito.verify<ArticleInteractor>(articlesInteractorMock, Mockito.times(1)).getArticleList()
        Mockito.verify<ArticleInteractor>(articlesInteractorMock, Mockito.times(1)).clearCache()
        Mockito.verify<ILocalStorageRepository>(localStorageRepository, Mockito.times(1)).addArticleDataListToCache(Mockito.any(ArticleDataList::class.java))
        Mockito.verify<IMarvelsRepository>(mMarvelsRepository, Mockito.times(1)).getArticleList()
        Mockito.verify<IArticlesListView>(articlesView, Mockito.times(2)).onLoadingStateChange(Mockito.anyBoolean())
        Mockito.verify<IArticlesListView>(articlesView, Mockito.times(1)).onArticlesLoaded(Mockito.any())
    }

    @Test
    fun Verify_Method_Calls_When_Loading_From_Server_Refresh_False() {

        doReturn(Single.just(ArticleDataList.empty())).`when`<ILocalStorageRepository>(localStorageRepository).getArticleDataListFromCache()
        doReturn(Single.just<List<ArticleApiData>>(serverItems)).`when`<IMarvelsRepository>(mMarvelsRepository).getArticleList()

        doAnswer(object : Answer <Single<ArticleDataList>>{
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ArticleDataList> ? {

                cachedItems.articles!!.clear()
                return null
            }
        }).`when`<ILocalStorageRepository>(localStorageRepository).dropArticleDataListCache()

        doAnswer(object : Answer <Single<ArticleDataList> > {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ArticleDataList>  {
                return articlesInteractor!!.getArticleList()
            }
        }).`when`<ArticleInteractor>(articlesInteractorMock).getArticleList()

        doAnswer(object : Answer<Any> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Any {
                return articlesInteractor!!.clearCache()
            }
        }).`when`<ArticleInteractor>(articlesInteractorMock).clearCache()

        doAnswer(object : Answer <Single<ArticleDataList>>{
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ArticleDataList> {

                val articleDataList = invocation.getArgument<ArticleDataList>(0)
                return Single.just(articleDataList)
            }
        }).`when`<ILocalStorageRepository>(localStorageRepository).addArticleDataListToCache(Mockito.any(ArticleDataList::class.java))

        doAnswer(object : Answer <ArrayList<ArticleViewData>>{
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): ArrayList<ArticleViewData>? {

                val arrayList = invocation.getArgument<ArrayList<ArticleViewData>>(0)
                articleItems.clear()
                articleItems.addAll(arrayList)
                return null
            }
        }).`when`<IArticlesListView>(articlesView).onArticlesLoaded(Mockito.any())

        articlesPresenter!!.loadArticles(false)
        testScheduler.triggerActions()

        Mockito.verify<ArticleInteractor>(articlesInteractorMock, Mockito.times(1)).getArticleList()
        Mockito.verify<ArticleInteractor>(articlesInteractorMock, Mockito.times(0)).clearCache()
        Mockito.verify<ILocalStorageRepository>(localStorageRepository, Mockito.times(1)).getArticleDataListFromCache()
        Mockito.verify<ILocalStorageRepository>(localStorageRepository, Mockito.times(1)).addArticleDataListToCache(Mockito.any(ArticleDataList::class.java))
        Mockito.verify<IMarvelsRepository>(mMarvelsRepository, Mockito.times(1)).getArticleList()
        Mockito.verify<IArticlesListView>(articlesView, Mockito.times(2)).onLoadingStateChange(Mockito.anyBoolean())
        Mockito.verify<IArticlesListView>(articlesView, Mockito.times(1)).onArticlesLoaded(Mockito.any())
    }
}

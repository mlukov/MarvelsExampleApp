package com.mlukov.marvels

import com.mlukov.marvels.repositories.remote.model.ComicRemote
import com.mlukov.marvels.domain.interactors.ComicInteractor
import com.mlukov.marvels.domain.models.Comic
import com.mlukov.marvels.domain.models.ComicList
import com.mlukov.marvels.domain.repositories.remote.IMarvelsRemoteRepository
import com.mlukov.marvels.domain.repositories.local.IComicsLocalRepository
import com.mlukov.marvels.presentation.comic.list.model.ComicViewData
import com.mlukov.marvels.presentation.comic.list.presenter.ComicListPresenter
import com.mlukov.marvels.presentation.comic.list.view.IComicListView
import com.mlukov.marvels.presentation.comic.list.model.ComicListViewModel
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
    private val comicView: IComicListView? = null

    @Mock
    val resourceProvider : IResourceProvider? = null

    @Mock
    val networkInfoProvider : INetworkInfoProvider ? = null

    @Mock
    private val comicsLocalRepository: IComicsLocalRepository? = null

    @Mock
    private val mMarvelsRemoteRepository: IMarvelsRemoteRepository? = null

    @Mock
    private val articlesInteractorMock: ComicInteractor? = null

    @Mock
    private val schedulersProvider: ISchedulersProvider? = null

    @InjectMocks
    private val articlesPresenter: ComicListPresenter? = null

    @InjectMocks
    private val articlesInteractor: ComicInteractor? = null

    private val testScheduler = TestScheduler()

    private val cachedItems = ComicList()
    private val serverItems = mutableListOf<ComicRemote>()

    private val articleItems = mutableListOf<ComicViewData>()

    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)

        doReturn(testScheduler).`when`<ISchedulersProvider>(schedulersProvider).ioScheduler()
        doReturn(testScheduler).`when`<ISchedulersProvider>(schedulersProvider).uiScheduler()
        doReturn(testScheduler).`when`<ISchedulersProvider>(schedulersProvider).computationScheduler()

        val comicRemote = ComicRemote()

        comicRemote.id = 2
        comicRemote.title = "Title2"
        comicRemote.subtitle = "Subtitle2"
        comicRemote.date = Date("1982/01/21 18:41")

        val articleData = Comic()
        articleData.id = 1
        articleData.title = "TITLE1"
        articleData.subtitle = "SUBTTITLE1"
        articleData.date = Date("1980/01/21 18:41")

        val cachedData = mutableListOf<Comic>()
        cachedData.add(articleData)
        cachedItems.comics = cachedData
        serverItems.add(comicRemote)
    }

    @Test
    fun Should_Load_Articles_List_Into_View() {

        doReturn(Single.just(cachedItems)).`when`<IComicsLocalRepository>(comicsLocalRepository).getComicList()
        doReturn(Single.just<List<ComicRemote>>(serverItems)).`when`<IMarvelsRemoteRepository>(mMarvelsRemoteRepository).getComicsList()
        doAnswer(object : Answer<Single<ComicList>> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ComicList> {
                return articlesInteractor!!.getComicList()
            }
        }).`when`<ComicInteractor>(articlesInteractorMock).getComicList()

        doAnswer(object : Answer<ArrayList<ComicViewData>> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): ArrayList<ComicViewData>? {

                val arrayList = invocation.getArgument<ArrayList<ComicViewData>>(0)
                articleItems.clear()
                articleItems.addAll(arrayList)
                return null
            }
        }).`when`<IComicListView>(comicView).onArticlesLoaded(Mockito.any() )

        articlesPresenter!!.loadArticles(false)
        testScheduler.triggerActions()

        val repositoryItem = articleItems[0]

        Assert.assertNotNull(repositoryItem)
    }

    @Test
    fun Should_Load_Articles_List_From_Cache() {

        doReturn(Single.just(cachedItems)).`when`<IComicsLocalRepository>(comicsLocalRepository).getComicList()
        doReturn(Single.just<List<ComicRemote>>(serverItems)).`when`<IMarvelsRemoteRepository>(mMarvelsRemoteRepository).getComicsList()
        doAnswer(object : Answer  <Single<ComicList>> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock):  Single<ComicList> {
                return articlesInteractor!!.getComicList()
            }
        }).`when`<ComicInteractor>(articlesInteractorMock).getComicList()

        doAnswer(object : Answer < ComicListViewModel> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): ComicListViewModel? {

                val arrayList = invocation.getArgument<ArrayList<ComicViewData>>(0)
                articleItems.clear()
                articleItems.addAll(arrayList)
                return null
            }
        }).`when`<IComicListView>(comicView).onArticlesLoaded(Mockito.any())

        articlesPresenter!!.loadArticles(false)
        testScheduler.triggerActions()

        val repositoryItem = articleItems[0]

        Assert.assertEquals("Item not loaded from cache", "TITLE1", repositoryItem.title)
    }

    @Test
    fun Should_Load_Articles_List_From_Server_Refresh_True() {

        doReturn(Single.just(cachedItems)).`when`<IComicsLocalRepository>(comicsLocalRepository).getComicList()
        doReturn(Single.just<List<ComicRemote>>(serverItems)).`when`<IMarvelsRemoteRepository>(mMarvelsRemoteRepository).getComicsList()

        doAnswer(object : Answer <Any>{
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Any? {

                cachedItems.comics!!.clear()
                return null
            }
        }).`when`<IComicsLocalRepository>(comicsLocalRepository).dropArticleDataListCache()

        doAnswer(object : Answer <Single<ComicList> > {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ComicList>  {
                return articlesInteractor!!.getComicList()
            }
        }).`when`<ComicInteractor>(articlesInteractorMock).getComicList()

        doAnswer(object : Answer <Any> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Any {
                return articlesInteractor!!.clearCache()
            }
        }).`when`<ComicInteractor>(articlesInteractorMock).clearCache()

        doAnswer(object : Answer <Single<ComicList> > {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ComicList>   {

                val articles = invocation.getArgument<ComicList>(0)
                return Single.just(articles)
            }
        }).`when`<IComicsLocalRepository>(comicsLocalRepository).updateComicList(Mockito.any(ComicList::class.java))

        doAnswer(object : Answer <ArrayList<ComicViewData>> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): ArrayList<ComicViewData>? {

                val arrayList = invocation.getArgument<ArrayList<ComicViewData>>(0)
                articleItems.clear()
                articleItems.addAll(arrayList)
                return null
            }
        }).`when`<IComicListView>(comicView).onArticlesLoaded(Mockito.any())

        articlesPresenter!!.loadArticles(true)
        testScheduler.triggerActions()

        val repositoryItem = articleItems[0]

        Assert.assertEquals("Item not loaded from cache", "TITLE1", repositoryItem.title)
    }

    @Test
    fun Should_Load_Articles_List_From_Server_Refresh_False_No_Cached_Items() {

        doReturn(Single.just(ComicList.empty())).`when`<IComicsLocalRepository>(comicsLocalRepository).getComicList()
        doReturn(Single.just<List<ComicRemote>>(serverItems)).`when`<IMarvelsRemoteRepository>(mMarvelsRemoteRepository).getComicsList()

        doAnswer(object : Answer <Any> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Any? {

                cachedItems.comics!!.clear()
                return null
            }
        }).`when`<IComicsLocalRepository>(comicsLocalRepository).dropArticleDataListCache()

        doAnswer(object : Answer <Any> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Any {
                return articlesInteractor!!.getComicList()
            }
        }).`when`<ComicInteractor>(articlesInteractorMock).getComicList()

        doAnswer(object : Answer <Any> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Any {
                return articlesInteractor!!.clearCache()
            }
        }).`when`<ComicInteractor>(articlesInteractorMock).clearCache()

        doAnswer(object : Answer <Single<ComicList> >{
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ComicList>  {

                val articles = invocation.getArgument<ComicList>(0)
                return Single.just(articles)
            }
        }).`when`<IComicsLocalRepository>(comicsLocalRepository).updateComicList(Mockito.any(ComicList::class.java))

        doAnswer(object : Answer <ArrayList<ComicViewData>> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): ArrayList<ComicViewData>? {

                val arrayList = invocation.getArgument<ArrayList<ComicViewData>>(0)
                articleItems.clear()
                articleItems.addAll(arrayList)
                return null
            }
        }).`when`<IComicListView>(comicView).onArticlesLoaded(Mockito.any())

        articlesPresenter!!.loadArticles(false)
        testScheduler.triggerActions()

        val repositoryItem = articleItems[0]

        Assert.assertEquals("Item not loaded from cache", "Title2", repositoryItem.title)
    }

    @Test
    fun Verify_Method_Calls_When_Loading_From_Cache() {

        doReturn(Single.just(cachedItems)).`when`<IComicsLocalRepository>(comicsLocalRepository).getComicList()
        doReturn(Single.just<List<ComicRemote>>(serverItems)).`when`<IMarvelsRemoteRepository>(mMarvelsRemoteRepository).getComicsList()
        doAnswer(object : Answer <Single<ComicList> > {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ComicList> {
                return articlesInteractor!!.getComicList()
            }
        }).`when`<ComicInteractor>(articlesInteractorMock).getComicList()

        doAnswer(object : Answer <ArrayList<ComicViewData>> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): ArrayList<ComicViewData>? {

                val arrayList = invocation.getArgument<ArrayList<ComicViewData>>(0)
                articleItems.clear()
                articleItems.addAll(arrayList)
                return null
            }
        }).`when`<IComicListView>(comicView).onArticlesLoaded(Mockito.any())

        articlesPresenter!!.loadArticles(false)
        testScheduler.triggerActions()

        Mockito.verify<ComicInteractor>(articlesInteractorMock, Mockito.times(1)).getComicList()
        Mockito.verify<ComicInteractor>(articlesInteractorMock, Mockito.times(0)).clearCache()
        Mockito.verify<IComicsLocalRepository>(comicsLocalRepository, Mockito.times(1)).getComicList()
        Mockito.verify<IComicListView>(comicView, Mockito.times(2)).onLoadingStateChange(Mockito.anyBoolean())
        Mockito.verify<IComicListView>(comicView, Mockito.times(1)).onArticlesLoaded(Mockito.any())
    }

    @Test
    fun Verify_Method_Calls_When_Loading_From_Server_Refresh_True() {

        doReturn(Single.just(cachedItems)).`when`<IComicsLocalRepository>(comicsLocalRepository).getComicList()
        doReturn(Single.just<List<ComicRemote>>(serverItems)).`when`<IMarvelsRemoteRepository>(mMarvelsRemoteRepository).getComicsList()

        doAnswer(object : Answer <Single<ComicList> > {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ComicList> ? {

                cachedItems.comics!!.clear()
                return null
            }
        }).`when`<IComicsLocalRepository>(comicsLocalRepository).dropArticleDataListCache()

        doAnswer(object : Answer <Single<ComicList> > {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ComicList>  {
                return articlesInteractor!!.getComicList()
            }
        }).`when`<ComicInteractor>(articlesInteractorMock).getComicList()

        doAnswer(object : Answer <Any> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Any {
                return articlesInteractor!!.clearCache()
            }
        }).`when`<ComicInteractor>(articlesInteractorMock).clearCache()

        doAnswer(object : Answer <Single<ComicList> > {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ComicList>  {

                val articleDataList = invocation.getArgument<ComicList>(0)
                return Single.just(articleDataList)
            }
        }).`when`<IComicsLocalRepository>(comicsLocalRepository).updateComicList(Mockito.any(ComicList::class.java))

        doAnswer(object : Answer <ArrayList<ComicViewData>> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): ArrayList<ComicViewData>? {

                val arrayList = invocation.getArgument<ArrayList<ComicViewData>>(0)
                articleItems.clear()
                articleItems.addAll(arrayList)
                return null
            }
        }).`when`<IComicListView>(comicView).onArticlesLoaded(Mockito.any())

        articlesPresenter!!.loadArticles(true)
        testScheduler.triggerActions()

        Mockito.verify<ComicInteractor>(articlesInteractorMock, Mockito.times(1)).getComicList()
        Mockito.verify<ComicInteractor>(articlesInteractorMock, Mockito.times(1)).clearCache()
        Mockito.verify<IComicsLocalRepository>(comicsLocalRepository, Mockito.times(1)).updateComicList(Mockito.any(ComicList::class.java))
        Mockito.verify<IMarvelsRemoteRepository>(mMarvelsRemoteRepository, Mockito.times(1)).getComicsList()
        Mockito.verify<IComicListView>(comicView, Mockito.times(2)).onLoadingStateChange(Mockito.anyBoolean())
        Mockito.verify<IComicListView>(comicView, Mockito.times(1)).onArticlesLoaded(Mockito.any())
    }

    @Test
    fun Verify_Method_Calls_When_Loading_From_Server_Refresh_False() {

        doReturn(Single.just(ComicList.empty())).`when`<IComicsLocalRepository>(comicsLocalRepository).getComicList()
        doReturn(Single.just<List<ComicRemote>>(serverItems)).`when`<IMarvelsRemoteRepository>(mMarvelsRemoteRepository).getComicsList()

        doAnswer(object : Answer <Single<ComicList>>{
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ComicList> ? {

                cachedItems.comics!!.clear()
                return null
            }
        }).`when`<IComicsLocalRepository>(comicsLocalRepository).dropArticleDataListCache()

        doAnswer(object : Answer <Single<ComicList> > {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ComicList>  {
                return articlesInteractor!!.getComicList()
            }
        }).`when`<ComicInteractor>(articlesInteractorMock).getComicList()

        doAnswer(object : Answer<Any> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Any {
                return articlesInteractor!!.clearCache()
            }
        }).`when`<ComicInteractor>(articlesInteractorMock).clearCache()

        doAnswer(object : Answer <Single<ComicList>>{
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Single<ComicList> {

                val articleDataList = invocation.getArgument<ComicList>(0)
                return Single.just(articleDataList)
            }
        }).`when`<IComicsLocalRepository>(comicsLocalRepository).updateComicList(Mockito.any(ComicList::class.java))

        doAnswer(object : Answer <ArrayList<ComicViewData>>{
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): ArrayList<ComicViewData>? {

                val arrayList = invocation.getArgument<ArrayList<ComicViewData>>(0)
                articleItems.clear()
                articleItems.addAll(arrayList)
                return null
            }
        }).`when`<IComicListView>(comicView).onArticlesLoaded(Mockito.any())

        articlesPresenter!!.loadArticles(false)
        testScheduler.triggerActions()

        Mockito.verify<ComicInteractor>(articlesInteractorMock, Mockito.times(1)).getComicList()
        Mockito.verify<ComicInteractor>(articlesInteractorMock, Mockito.times(0)).clearCache()
        Mockito.verify<IComicsLocalRepository>(comicsLocalRepository, Mockito.times(1)).getComicList()
        Mockito.verify<IComicsLocalRepository>(comicsLocalRepository, Mockito.times(1)).updateComicList(Mockito.any(ComicList::class.java))
        Mockito.verify<IMarvelsRemoteRepository>(mMarvelsRemoteRepository, Mockito.times(1)).getComicsList()
        Mockito.verify<IComicListView>(comicView, Mockito.times(2)).onLoadingStateChange(Mockito.anyBoolean())
        Mockito.verify<IComicListView>(comicView, Mockito.times(1)).onArticlesLoaded(Mockito.any())
    }
}

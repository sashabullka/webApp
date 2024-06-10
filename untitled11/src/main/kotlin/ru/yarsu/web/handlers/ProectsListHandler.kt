package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.findSingle
import org.http4k.core.queries
import org.http4k.routing.path
import org.http4k.template.TemplateRenderer
import ru.yarsu.domain.GetProectByName
import ru.yarsu.domain.GetVotesByPage
import ru.yarsu.domain.Paginator
import ru.yarsu.domain.Proect
import ru.yarsu.domain.ProectsStorage
import ru.yarsu.domain.VotesStorage
import ru.yarsu.web.models.NotFoundVM
import ru.yarsu.web.models.ProectsListVM

class ProectsListHandler(rendererConst: TemplateRenderer, listProectsConst: List<Proect>, getObjectByPageConst: GetProectByName, getVoteByPageConst: GetVotesByPage) : HttpHandler {
    val renderer: TemplateRenderer = rendererConst
    private val listProects: List<Proect> = listProectsConst
    private val getProectByName = getObjectByPageConst
    private val getVoteByPage = getVoteByPageConst
    override fun invoke(request: Request): Response {
        val name: String? = request.path("proect")
        if (name == null || name.isEmpty() || name !in ProectsStorage(listProects).listNames()) {
            return Response(Status.NOT_FOUND).body(renderer(NotFoundVM()))
        }
        val proect = getProectByName.get(name)

        val page: Int? = request.path("page")?.toIntOrNull()
        if (page == null || page < 1 || page > ProectsStorage(listProects).countPage() || page.toString().isEmpty()) {
            return Response(Status.NOT_FOUND).body(renderer(NotFoundVM()))
        }
        val voteslist = getVoteByPage.get(page,proect)

        val parametrs = request.uri.queries()
        val minStr = parametrs.findSingle("min")
        val maxStr = parametrs.findSingle("max")
        val min: Double? = if (!minStr.isNullOrEmpty()) minStr.toDouble() else null
        val max: Double? = if (!maxStr.isNullOrEmpty()) maxStr.toDouble() else null
        val votesListForFilter = VotesStorage(voteslist)


        val url = "/proects/" + proect.getNamePunkt()+"/num" + page.toString()
        val paginator = Paginator(url, page, ProectsStorage(listProects).countPage())


        val viewModel = ProectsListVM(proect, votesListForFilter.filter(min,max), paginator, page,listProects, min, max, votesListForFilter.ifEmptyFilter(min,max))
        val htmlDocument = renderer(viewModel)
        return Response(Status.OK).body(htmlDocument)
    }
}
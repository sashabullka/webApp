package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Uri
import org.http4k.core.appendToPath
import org.http4k.core.findSingle
import org.http4k.core.queries
import org.http4k.routing.path
import org.http4k.template.TemplateRenderer
import ru.yarsu.domain.GetProectByName
import ru.yarsu.domain.Paginator
import ru.yarsu.domain.Proect
import ru.yarsu.domain.ProectsStorage
import ru.yarsu.domain.VotesStorage
import ru.yarsu.web.models.NotFoundVM
import ru.yarsu.web.models.ProectsListVM

class ProectsListHandler(val renderer: TemplateRenderer,val storage: ProectsStorage, val listProects: MutableList<Proect>, val getProectByName: GetProectByName) : HttpHandler {

    override fun invoke(request: Request): Response {
        val name: String = request.path("proect") ?: return Response(Status.NOT_FOUND)
        if (name.isEmpty() || name !in ProectsStorage(listProects).listNames()) {
            return Response(Status.NOT_FOUND)
        }
        val proect = getProectByName.get(name)

        val page: Int = request.path("page")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (page < 1 || page > ProectsStorage(listProects).countPage() || page.toString().isEmpty()) {
            return Response(Status.NOT_FOUND)
        }
        val voteslist = storage.getVotesByPageNum(page,proect)

        val parametrs = request.uri.queries()
        val minStr = parametrs.findSingle("min")
        val maxStr = parametrs.findSingle("max")
        if (minStr != null) {
            if(!(minStr.all { it.isDigit() }) && minStr.isNotEmpty()){
                return Response(Status.NOT_FOUND)
            }
        }

        if (maxStr != null) {
            if(!(maxStr.all { it.isDigit() }) && maxStr.isNotEmpty()){
                return Response(Status.NOT_FOUND)
            }
        }


        val min: Int? = if (!minStr.isNullOrEmpty()) minStr.toInt() else null
        val max: Int? = if (!maxStr.isNullOrEmpty()) maxStr.toInt() else null

        val votesListForFilter = VotesStorage(voteslist)


        val baseUrl = Uri.of("http://localhost:9000")
        val pathUrl = baseUrl
            .path(proect.getProectID())
            .appendToPath("n$page")
        val finalUrl = pathUrl.toString()

        val paginator = Paginator(finalUrl, page, ProectsStorage(listProects).countPage())


        val viewModel = ProectsListVM(proect, votesListForFilter.filter(min,max), paginator, page,listProects, min, max, votesListForFilter.ifEmptyFilter(min,max))
        val htmlDocument = renderer(viewModel)
        return Response(Status.OK).body(htmlDocument)
    }
}
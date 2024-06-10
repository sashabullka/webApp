package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.findSingle
import org.http4k.core.queries
import org.http4k.core.with
import org.http4k.routing.path
import ru.yarsu.operations.GetProectByName
import ru.yarsu.operations.Paginator
import ru.yarsu.domain.Proect
import ru.yarsu.storages.ProectsStorage

import ru.yarsu.web.models.ProectsListVM
import ru.yarsu.web.templates.ContextAwareViewRender

class ProectsListHandler(val renderer: ContextAwareViewRender, val storage: ProectsStorage, val listProects: MutableList<Proect>, val getProectByName: GetProectByName) : HttpHandler {

    override fun invoke(request: Request): Response {
        val nameP: Int = request.path("proect")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (nameP.toString().isEmpty() || nameP.toString() !in storage.listNames()) {
            return Response(Status.NOT_FOUND)
        }
        val proect = getProectByName.get(nameP)

        val page: Int = request.path("page")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (page < 1 || page > storage.countPage() || page.toString().isEmpty()) {
            return Response(Status.NOT_FOUND)
        }

        val parametrs = request.uri.queries()
        val minStr = parametrs.findSingle("min")
        val maxStr = parametrs.findSingle("max")
        if (minStr != null) {
            if((!(minStr.all { it.isDigit() }) && minStr.isNotEmpty()) || minStr.length > 8){
                return Response(Status.NOT_FOUND)
            }
        }

        if (maxStr != null) {
            if((!(maxStr.all { it.isDigit() }) && maxStr.isNotEmpty()) || maxStr.length > 8){
                return Response(Status.NOT_FOUND)
            }
        }


        val min: Int? = if (!minStr.isNullOrEmpty()) minStr.toInt() else null
        val max: Int? = if (!maxStr.isNullOrEmpty()) maxStr.toInt() else null

        val voteslist = storage.getVotesByPageNum(page,proect, min, max)
        val empty = storage.ifEmptyFilter(min,max,proect,page)




        val paginator = Paginator(request.uri, page, storage.countPagePaginator(min,max,proect))


        val viewModel = ProectsListVM(proect, voteslist, paginator, page, min, max, empty)
        return Response(Status.OK).with(renderer(request) of viewModel)
    }
}
package dev.mayutama.project.testnewsapp.domain.useCase.source

import javax.inject.Inject

data class SourceUseCase @Inject constructor(
    val getAllSourceUseCase: GetAllSourceUseCase,
    val refreshSourceUseCase: RefreshSourceUseCase
)
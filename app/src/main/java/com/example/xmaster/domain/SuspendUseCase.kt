import com.example.xmaster.utils.Result


abstract class SuspendUseCase<in Param, R>() {

    suspend operator fun invoke(parameters: Param): Result<R> {
        return try {
            execute(parameters).let {
                Result.Success(it)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: Param): R
}

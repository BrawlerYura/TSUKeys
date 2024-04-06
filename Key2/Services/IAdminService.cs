using Key2.Models;

namespace Key2.Services
{
    public interface IAdminService
    {
        Task<TokenResponseModel> RegisterAsync(RegisterDeanModel RegisterModel);
        Task<TokenResponseModel> LoginAsync(LoginCredentialsModel model);
        Task<List<Dean>> ShowDeans();
        Task ActiveDean(Guid userId,Guid deanId, bool isActive);

        Task LogOutAsync(TokenBan token);
    }
}

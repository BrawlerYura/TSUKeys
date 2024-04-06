using Key2.Models;
using Key2.Services;
using Microsoft.AspNetCore.Mvc;
using System.Security.Claims;

namespace Key2.Controllers
{
    [Route("api/admin")]
    public class AdminController : Controller
    {
        private readonly IAdminService _adminService;
        private readonly IBannedTokenService _banTokensService;

        public AdminController(IAdminService adminService, IBannedTokenService banTokensService)
        {
            _adminService = adminService;
            _banTokensService = banTokensService;
        }
        [Route("login")]
        [HttpPost]
        public async Task<ActionResult> Login([FromBody] LoginCredentialsModel model) 
        {
            try
            {
                var result = await _adminService.LoginAsync(model);
                return Ok(result);
            }
            catch (ArgumentException ex)
            {
                return StatusCode(400, ex.Message);
            }
            catch (Exception ex)
            {
                return StatusCode(500, "Internal server error");
            }
        }
        [Route("register")]
        [HttpPost]
        public async Task<ActionResult> Register([FromBody] RegisterDeanModel model)
        {
            try
            {
                var res = await _adminService.RegisterAsync(model);
                return Ok(res);
            }
            catch (ArgumentException ex)
            {
                return StatusCode(400, ex.Message);
            }
            catch (Exception ex)
            {
                return StatusCode(500, "Internal server error");
            }
        }
        [Route("deans")]
        [HttpGet]
        public async Task<ActionResult> GetDeans()
        {
            var res = await _adminService.ShowDeans();
            return Ok(res);
        }
        [Route("activate")]
        [HttpGet]
        public async Task<ActionResult> ActivateDean(Guid id, bool isActive)
        {
            Guid userId = Guid.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier));
            try
            {
                await _adminService.ActiveDean(userId, id, isActive);
                return Ok();
            }
            catch (Exception ex) 
            {
                return StatusCode(500, ex.Message);
            }
        }
    }
}

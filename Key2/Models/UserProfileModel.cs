using Keys.Models.Enums;

namespace Key2.Models
{
    public class UserProfileModel
    {
        public string Name { get; set; }
        public string Email { get; set; }
        public DateTime? BirthDate { get; set; }
        public Role Role { get; set; }
        public bool IsActive { get; set; }
        public bool? IsDeanWorker { get; set; }
        public bool IsDenied { get; set; }  
    }
}

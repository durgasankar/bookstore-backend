export const registerRoute = {
    path: '/api/register',
    method: 'post',
    handler: async (req, res) => {
        res.json({message: 'OK'})
    }
}
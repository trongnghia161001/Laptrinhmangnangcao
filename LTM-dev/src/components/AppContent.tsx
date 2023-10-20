import React, { Suspense } from "react";
import { Navigate, Route, Routes } from "react-router-dom";

// routes config
import routes from "@/routes";

const ADMIN_ID = null;

const AppContent = () => {

  return (
    <Suspense>
      <Routes>
        {/* {renderAuthorRoutes(currentUser?.role)} */}
        {/* {routes.map((route: any, idx) => {
          if (currentUser?.hotelId === ADMIN_ID && route?.element) {
            return (
              <Route key={idx} path={route.path} element={<route.element />} />
            );
          }

          return route?.element ? (
            route?.role?.includes(currentUser?.role) && (
              <Route key={idx} path={route.path} element={<route.element />} />
            )
          ) : (
            <Route path="*" element={<Navigate to="/403" replace />} />
          );
        })} */}

        {routes.map((route: any, idx) => <Route key={idx} path={route.path} element={<route.element />} />
        )}

        <Route path="*" element={<Navigate to="/404" replace />} />
      </Routes>
    </Suspense>
  );
};

export default React.memo(AppContent);
